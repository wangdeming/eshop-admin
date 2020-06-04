package cn.ibdsr.web.modular.shop.order.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.order.DeliveryStatus;
import cn.ibdsr.web.common.constant.state.order.RefundStatus;
import cn.ibdsr.web.common.constant.state.order.RefundType;
import cn.ibdsr.web.common.constant.state.order.ReviewOperType;
import cn.ibdsr.web.common.constant.state.order.ServiceStatus;
import cn.ibdsr.web.common.constant.state.order.ShopOrderStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopDeliveryAddressMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderGoodsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundMapper;
import cn.ibdsr.web.common.persistence.model.Payment;
import cn.ibdsr.web.common.persistence.model.ShopDeliveryAddress;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderGoods;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefundLogistics;
import cn.ibdsr.web.core.mq.rabbitmq.MessageSenderTask;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.shop.order.service.IOrderRefundService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderRefundService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 订单售后管理Service
 * @Version V1.0
 * @CreateDate 2019-04-04 15:19:05
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-04 15:19:05    XuZhipeng               类说明
 */
@Service
public class OrderRefundServiceImpl implements IOrderRefundService {

    @Autowired
    private ShopOrderRefundMapper shopOrderRefundMapper;

    @Autowired
    private ShopOrderGoodsMapper shopOrderGoodsMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopDeliveryAddressMapper shopDeliveryAddressMapper;

    @Autowired
    private ShopOrderRefundService shopOrderRefundService;

    @Autowired
    private ICashTransferService cashTransferService;

    @Autowired
    private MessageSenderTask messageSenderTask;

    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 审核通过
     *
     * @param refundId     退款订单ID
     * @param reviewRemark 审核说明
     * @param shopAddrId   店铺收货地址
     */
    @Transactional
    @Override
    public void pass(Long refundId, String reviewRemark, Long shopAddrId) throws Exception {
        // 校验审核说明内容长度
        if (ToolUtil.isNotEmpty(reviewRemark)) {
            if (30 < reviewRemark.length()) {
                throw new BussinessException(BizExceptionEnum.REVIEW_REMARK_NOT_FORMAT);
            }
        }
        synchronized (this) {
            // 查询退款单是否存在
            ShopOrderRefund refund = checkRefundId(refundId);
            if (RefundStatus.WAIT_REVIEW.getCode() != refund.getStatus()) {
                throw new BussinessException(BizExceptionEnum.REFUND_ORDER_NOT_WAITING_REVIEW);
            }

            // 判断退款订单类型：仅退款-直接调用第三方退款接口；退货退款-退款订单状态修改为用户待发货状态
            if (RefundType.AMOUNT.getCode() == refund.getType()) {
                // 调用第三方退款接口
                Payment payment = shopOrderRefundService.payment(refund.getOrderId());
                Map<String, String> result = shopOrderRefundService.refund(refund, payment);
                if (!StringUtils.equals(Const.SUCCESS, result.get("return_code")) || !StringUtils.equals(Const.SUCCESS, result.get("result_code"))) {
                    throw new Exception(result.get("err_code_des"));
                }
                // 更新订单商品售后状态为已退款
                updateOrderGoodsStatus(refund.getOrderGoodsId(), ServiceStatus.REFUNDED.getCode());

                //返回库存
                Date now = new Date();
                ShiroUser user = ShiroKit.getUser();
                List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("order_id", refund.getOrderId()).eq("is_deleted", IsDeleted.NORMAL.getCode()));
                for (ShopOrderGoods r : shopOrderGoodsList) {
                    shopOrderRefundService.changeGoodsStock(r.getGoodsId(), r.getSkuId(), r.getNums(), user, now);
                }

                // 售后退款资金变动：待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
                cashTransferService.serviceRefundTransfer(refund.getShopId(), refund.getOrderId(), refund.getRefundAmount(), ShopType.STORE.getCode());

                // 发送消息：订单7天内无申请售后，自动结算
                messageSenderTask.sendMsgOfSettleShopOrder(refund.getOrderId());
            } else {
                // 新增退货物流信息
                insertRefundLogistics(refundId, shopAddrId);
                messageSenderTask.sendMsgOfRevokeShopOrderRefund(refundId);
            }

            // 更新退款订单状态：仅退款类型设置为已退款，退货退款类型设置为审核通过，等待用户发货
            int refundStatus = RefundType.AMOUNT.getCode() == refund.getType() ? RefundStatus.SUCCESS.getCode() : RefundStatus.PASS.getCode();
            updateOrderRefund(refundId, reviewRemark, refundStatus);

            // 更新订单状态
            updateOrderStatus(refund.getOrderId(), refund.getOrderGoodsId(), refund.getType(), ReviewOperType.PASS);


        }
    }

    /**
     * 审核不通过
     *
     * @param refundId     退款订单ID
     * @param reviewRemark 审核说明
     */
    @Transactional
    @Override
    public void refusePass(Long refundId, String reviewRemark) {
        // 查询退款单是否存在
        ShopOrderRefund refund = checkRefundId(refundId);
        if (RefundStatus.WAIT_REVIEW.getCode() != refund.getStatus()) {
            throw new BussinessException(BizExceptionEnum.REFUND_ORDER_NOT_WAITING_REVIEW);
        }
        // 校验审核说明内容长度
        if (ToolUtil.isEmpty(reviewRemark)) {
            throw new BussinessException(BizExceptionEnum.REVIEW_REMARK_IS_NULL);
        }
        if (200 < reviewRemark.length()) {
            throw new BussinessException(BizExceptionEnum.REVIEW_REMARK_NOT_FORMAT);
        }

        // 更新退款订单状态
        updateOrderRefund(refundId, reviewRemark, RefundStatus.REFUSE.getCode());

        // 更新订单商品售后状态为审核拒绝状态
        updateOrderGoodsStatus(refund.getOrderGoodsId(), ServiceStatus.REFUND_FAIL.getCode());

        // 更新订单状态
        updateOrderStatus(refund.getOrderId(), refund.getOrderGoodsId(), refund.getType(), ReviewOperType.REFUSE_PASS);
    }

    /**
     * 校验退款订单ID，并返回退款订单信息
     *
     * @param refundId 退款订单ID
     * @return
     */
    private ShopOrderRefund checkRefundId(Long refundId) {
        if (null == refundId) {
            throw new BussinessException(BizExceptionEnum.REFUND_ID_IS_NULL);
        }
        ShopOrderRefund refund = shopOrderRefundMapper.selectById(refundId);
        if (null == refund) {
            throw new BussinessException(BizExceptionEnum.REFUND_ORDER_IS_NOT_EXIST);
        }
        return refund;
    }

    /**
     * 更新退款订单
     *
     * @param refundId     退款订单ID
     * @param reviewRemark 审核说明
     * @param refundStatus 退款订单状态
     */
    private void updateOrderRefund(Long refundId, String reviewRemark, Integer refundStatus) {
        ShopOrderRefund refund = new ShopOrderRefund();
        refund.setId(refundId);
        refund.setReviewRemark(reviewRemark);
        refund.setStatus(refundStatus);

        // 退款成功（审核通过且仅退款模式），更新预计到账时间
        if (RefundStatus.SUCCESS.getCode() != refundStatus) {
            refund.setExpectedTime(new Date());               // 预计到账时间（默认实时到账）
        }

        refund.setReviewUserId(ShiroKit.getUser().getId());
        refund.setReviewTime(new Date());
        refund.setModifiedUser(ShiroKit.getUser().getId());
        refund.setModifiedTime(new Date());
        shopOrderRefundMapper.updateById(refund);
    }

    /**
     * 更新订单商品状态
     *
     * @param orderGoodsId  订单商品ID
     * @param serviceStatus 售后状态
     */
    private void updateOrderGoodsStatus(Long orderGoodsId, Integer serviceStatus) {
        ShopOrderGoods orderGoods = new ShopOrderGoods();
        orderGoods.setId(orderGoodsId);
        orderGoods.setServiceStatus(serviceStatus);
        orderGoods.setModifiedTime(new Date());
        orderGoods.setModifiedUser(ShiroKit.getUser().getId());
        shopOrderGoodsMapper.updateById(orderGoods);
    }

    /**
     * 更新订单状态
     *
     * @param orderId      订单ID
     * @param orderGoodsId 售后状态
     * @param refundType   退款类型（1-仅退款；2-退货退款；）
     * @param operType     审核操作类型
     */
    private void updateOrderStatus(Long orderId, Long orderGoodsId, Integer refundType, ReviewOperType operType) {
        // 不存在其他无售后的订单商品，修改订单状态
        boolean isLastService = shopOrderService.checkGoodsIsLastService(orderId, orderGoodsId);
        if (!isLastService) {   // 存在其他售后商品，不对总订单做处理
            return;
        }

        // 查询订单信息
        ShopOrder order = shopOrderMapper.selectById(orderId);
        if (null == order) {
            return;
        }

        // 根据审核操作类型和退款订单类型查询获取订单状态
        Integer orderStatus;
        if (ReviewOperType.REFUSE_PASS == operType) {
            orderStatus = order.getPreStatus();
        } else {
            if (RefundType.AMOUNT_GOODS.getCode() == refundType) {  // 判断退款类型为退货退款，暂不修改订单状态
                return;
            }
            orderStatus = ShopOrderStatus.CLOSED.getCode();
        }

        order.setStatus(orderStatus);
        order.setModifiedTime(new Date());
        order.setModifiedUser(ShiroKit.getUser().getId());
        order.updateById();
    }

    /**
     * 新增退货地址信息
     *
     * @param refundId   退款订单ID
     * @param shopAddrId 店铺收货地址ID
     */
    private void insertRefundLogistics(Long refundId, Long shopAddrId) {
        if (null == shopAddrId) {
            throw new BussinessException(BizExceptionEnum.SHOP_DELIVERY_ADDR_ID_IS_NULL);
        }
        // 查询店铺收货信息
        ShopDeliveryAddress shopAddr = shopDeliveryAddressMapper.selectById(shopAddrId);
        if (null == shopAddr) {
            throw new BussinessException(BizExceptionEnum.SHOP_DELIVERY_IS_NOT_EXIST);
        }

        // 新增退货地址信息
        ShopOrderRefundLogistics refundLogistics = new ShopOrderRefundLogistics();
        refundLogistics.setRefundId(refundId);                                              // 退款记录ID
        refundLogistics.setProvince(shopAddr.getProvince());                                // 收货：省
        refundLogistics.setCity(shopAddr.getCity());                                        // 收货：市
        refundLogistics.setDistrict(shopAddr.getDistrict());                                // 收货：区
        refundLogistics.setAddress(shopAddr.getAddress());                                  // 收货地址
        refundLogistics.setConsigneeName(shopAddr.getConsigneeName());                      // 收货人
        refundLogistics.setConsigneePhone(shopAddr.getConsigneePhone());                    // 收货人手机号
        refundLogistics.setStatus(DeliveryStatus.WAIT_DELIVERY.getCode());                  // 待发货状态
        refundLogistics.setCreatedTime(new Date());
        refundLogistics.setCreatedUser(ShiroKit.getUser().getId());
        refundLogistics.setIsDeleted(IsDeleted.NORMAL.getCode());
        refundLogistics.insert();
    }
}
