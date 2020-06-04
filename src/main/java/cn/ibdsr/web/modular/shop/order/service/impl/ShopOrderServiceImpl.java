/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.order.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.order.DeliveryStatus;
import cn.ibdsr.web.common.constant.state.order.RefundStatus;
import cn.ibdsr.web.common.constant.state.order.RefundType;
import cn.ibdsr.web.common.constant.state.order.ServiceStatus;
import cn.ibdsr.web.common.constant.state.order.ShopOrderStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopDeliveryAddressMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderGoodsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundLogisticsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderSettlementMapper;
import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.common.persistence.model.GoodsEvaluate;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderGoods;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefundLogistics;
import cn.ibdsr.web.common.persistence.model.ShopOrderSettlement;
import cn.ibdsr.web.core.mq.rabbitmq.MessageSenderTask;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.cash.dao.SystemCashDao;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import cn.ibdsr.web.modular.shop.order.dao.ShopOrderDao;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderService;
import cn.ibdsr.web.modular.shop.order.transfer.EvaluateDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.GoodsVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderGoodsVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import cn.ibdsr.web.modular.shop.order.transfer.RefundDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.RefundLogisticsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/23 17:10
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/23      Zhujingrui               类说明
 */
@Service
public class ShopOrderServiceImpl implements ShopOrderService {

    @Autowired
    private ShopOrderDao shopOrderDao;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderGoodsMapper shopOrderGoodsMapper;

    @Autowired
    private ShopDeliveryAddressMapper addressMapper;

    @Autowired
    private ShopOrderRefundMapper shopOrderRefundMapper;

    @Autowired
    private ShopOrderRefundLogisticsMapper shopOrderRefundLogisticsMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ShopOrderSettlementMapper shopOrderSettlementMapper;

    @Autowired
    private SystemCashDao systemCashDao;

    @Autowired
    private IProfitDistributionService profitDistributionService;

    @Autowired
    private MessageSenderTask messageSenderTask;

    @Autowired
    private ICashTransferService cashTransferService;

    /**
     * 订单查询
     *
     * @param page             分页信息
     * @param orderNo          订单编号
     * @param consigneePhone   收件人手机号
     * @param consigneeName    收件人姓名
     * @param servicePhone     售后人手机号
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd   下单时间查询终止时间
     * @param goodsName        商品名称
     * @param orderStatus      查看订单的状态（0-全部；1-待付款；2-待发货；3-待收货；4-交易完成；5-已取消；6-售后中；7-交易关闭）
     * @return 返回订单列表
     */
    @Override
    public List<OrderVO> orderList(Page<OrderVO> page, String orderNo, String consigneePhone, String consigneeName, String servicePhone,
                                   String createdTimeStart, String createdTimeEnd, String goodsName, Integer orderStatus) {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        if (ToolUtil.isEmpty(shopId)) {
            throw new BussinessException(BizExceptionEnum.NO_THIS_USER);
        }
        if (createdTimeStart != null && createdTimeStart != "") {
            createdTimeStart = DateUtils.getStartTimeOfDay(createdTimeStart);
        }
        if (createdTimeEnd != null && createdTimeEnd != "") {
            createdTimeEnd = DateUtils.getEndTimeOfDay(createdTimeEnd);
        }

        List<OrderVO> orderVOList = shopOrderDao.orderList(page, shopId, orderNo, consigneePhone, consigneeName, servicePhone, createdTimeStart, createdTimeEnd, goodsName, orderStatus);
        for (OrderVO orderVO : orderVOList) {
            orderVO.setOrderPrice(AmountFormatUtil.amountFormat(orderVO.getOrderPrice()));
            orderVO.setExpressFee(AmountFormatUtil.amountFormat(orderVO.getExpressFee()));
            for (OrderGoodsVO orderGoodsVO : orderVO.getGoods()) {
                orderGoodsVO.setGoodsImg(ImageUtil.setImageURL(orderGoodsVO.getGoodsImg()));
                orderGoodsVO.setUnitPrice(AmountFormatUtil.amountFormat(orderGoodsVO.getUnitPrice()));
            }
        }
        return orderVOList;
    }

    /**
     * 订单详情
     *
     * @param orderId 订单ID
     * @return
     */
    @Override
    public List<OrderDetailVO> orderDetailList(Long orderId) {
        if (ToolUtil.isEmpty(orderId)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_ID_IS_NULL);
        }
        ShopOrder shopOrder = shopOrderMapper.selectById(orderId);
        if (shopOrder == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_IS_NOT_EXIST);
        }
        List<OrderDetailVO> orderDetailVOList = shopOrderDao.orderDetailList(orderId);
        for (OrderDetailVO orderDetailVO : orderDetailVOList) {
            orderDetailVO.setOrderPrice(AmountFormatUtil.amountFormat(orderDetailVO.getOrderPrice()));
            orderDetailVO.setExpressFee(AmountFormatUtil.amountFormat(orderDetailVO.getExpressFee()));
            orderDetailVO.setCouponAmount(AmountFormatUtil.amountFormat(orderDetailVO.getCouponAmount()));

            // 获取退款金额和结算金额
            BigDecimal refundAmount = systemCashDao.getOrderRefundAmount(orderId); // 查询订单完成退款总金额
            if (null == refundAmount) {
                refundAmount = BigDecimal.ZERO;
            }
            // 待到账金额
            BigDecimal accAmount = shopOrder.getOrderPrice().subtract(refundAmount);
            // 获取店铺服务费率
            BigDecimal serviceRate = profitDistributionService.getServiceRateByShopId(shopOrder.getShopId());
            // 服务费 = （订单金额 - 退款金额）* 服务费率
            BigDecimal serviceFee = accAmount.multiply(serviceRate);
            // 计算结算金额 = 订单金额 - 退款金额 - 服务费
            BigDecimal settleAmount = accAmount.subtract(serviceFee);

            orderDetailVO.setRefundAmount(AmountFormatUtil.amountFormat(refundAmount));
            orderDetailVO.setSettleAmount(AmountFormatUtil.amountFormat(settleAmount));

            if (ShopOrderStatus.WAIT_PAY.getCode() == orderDetailVO.getStatus()) {
                //用户下单时间
                Date userOrderTime = shopOrder.getCreatedTime();
                orderDetailVO.setOffTime(DateUtils.getDateTimeDiff(new Date(), DateUtils.getFailTime(userOrderTime, Const.ORDER_OUT_TIME)));
            }
            for (OrderGoodsVO orderGoodsVO : orderDetailVO.getGoods()) {
                orderGoodsVO.setGoodsImg(ImageUtil.setImageURL(orderGoodsVO.getGoodsImg()));
                orderGoodsVO.setUnitPrice(AmountFormatUtil.amountFormat(orderGoodsVO.getUnitPrice()));
            }

        }
        return orderDetailVOList;
    }

    /**
     * 统计当前店铺收货地址数量
     *
     * @return
     */
    @Override
    public int addressCount() {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();

        if (ToolUtil.isEmpty(shopId)) {
            throw new BussinessException(BizExceptionEnum.NO_THIS_USER);
        }

        int addressCount = addressMapper.selectCount(
                new EntityWrapper()
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );
        return addressCount;
    }

    /**
     * 获取快递公司集合
     *
     * @return
     */
    @Override
    public List<Dict> listExpressCompanys() {
        List<Dict> resultList = ConstantFactory.me().getSubListByName("快递公司");
        return resultList;
    }

    /**
     * 订单发货
     *
     * @param orderId        订单id
     * @param orderGoodsIds  订单商品id
     * @param expressCompany 快递公司
     * @param expressNo      快递单号
     * @return
     */
    @Override
    public void deliver(Long orderId, List<Long> orderGoodsIds, String expressCompany, String expressNo) {

        // 登录用户ID
        Long userId = ShiroKit.getUser().getId();

        //判断订单中，所有商品是否均已发货（或剩余商品状态为售后完成），true-是：修改订单状态为待收货
        boolean isLastDeliver = checkGoodsIsLastDeliver(orderId, orderGoodsIds);
        if (isLastDeliver) {
            ShopOrder order = new ShopOrder();
            order.setId(orderId);
            order.setStatus(ShopOrderStatus.WAIT_RECEIVE.getCode());
            order.setModifiedTime(new Date());
            order.setModifiedUser(userId);
            order.updateById();
        }

        for (Long orderGoodsId : orderGoodsIds) {
            //修改订单商品发货状态
            ShopOrderGoods orderGoods = new ShopOrderGoods();
            orderGoods.setId(orderGoodsId);
            orderGoods.setDeliveryStatus(DeliveryStatus.DELIVERED.getCode());
            orderGoods.setDeliveryTime(new Date());
            orderGoods.setExpressCompany(expressCompany);
            orderGoods.setExpressNo(expressNo);
            orderGoods.setModifiedTime(new Date());
            orderGoods.setModifiedUser(userId);
            orderGoods.updateById();
        }
    }

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return
     */
    @Override
    public void confirm(Long orderId) {
        if (ToolUtil.isEmpty(orderId)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_ID_IS_NULL);
        }
        ShopOrder shopOrder = shopOrderMapper.selectById(orderId);
        if (shopOrder == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_IS_NOT_EXIST);
        }
        List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("order_id", orderId).eq("is_deleted", IsDeleted.NORMAL.getCode()));
        if (shopOrderGoodsList == null || ToolUtil.isEmpty(shopOrderGoodsList)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_GOODS_IS_NOT_EXIST);
        }
        for (ShopOrderGoods r : shopOrderGoodsList) {
            r.setReceiveTime(new Date());
            r.setDeliveryStatus(DeliveryStatus.RECEIVED.getCode());
            r.updateById();
        }
        shopOrder.setModifiedTime(new Date());
        shopOrder.setStatus(ShopOrderStatus.RECEIVED.getCode());
        shopOrder.updateById();

        //用户确认收货：待出账金额-、待到账金额+（shop_order_cash_flow）
        cashTransferService.userConfirmTransfer(shopOrder.getShopId(), shopOrder.getId(), shopOrder.getOrderPrice(), ShopType.STORE.getCode());

        // 放入延时消息队列，处理订单7天内未发起售后，自动结算
        messageSenderTask.sendMsgOfSettleShopOrder(shopOrder.getId());
    }

    /**
     * 校验该商品在订单中是否为最后一件未发货商品
     *
     * @param orderId       订单ID
     * @param orderGoodsIds 订单商品ID
     * @return
     */
    private Boolean checkGoodsIsLastDeliver(Long orderId, List<Long> orderGoodsIds) {
        List<ShopOrderGoods> orderGoodsList = shopOrderGoodsMapper.selectList(
                new EntityWrapper<ShopOrderGoods>()
                        .eq("order_id", orderId)
                        .eq("delivery_status", DeliveryStatus.WAIT_DELIVERY.getCode())
                        .ne("service_status", ServiceStatus.REFUNDED.getCode())
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .notIn("id", orderGoodsIds));

        if (null == orderGoodsList || 0 == orderGoodsList.size()) {
            // 不存在其他无售后的订单商品，返回true
            return true;
        }
        return false;
    }

    /**
     * 订单中商品状态数量统计，主要是四种状态：1-已发货，2-未发货；3-退款中；4-退款完成
     *
     * @param orderId 订单ID
     * @return
     */
    @Override
    public Map<String, Integer> countOrderGoodsStatus(Long orderId) {
        Map<String, Integer> orderGoodsStatus = new HashMap<>();
        orderGoodsStatus.put("waitDelivery", 0);  //待发货
        orderGoodsStatus.put("delivered", 0);  //已发货
        orderGoodsStatus.put("refunding", 0);  //退款中
        orderGoodsStatus.put("refunded", 0);   //退款完成

        List<OrderDetailVO> orderDetailList = orderDetailList(orderId);
        for (OrderDetailVO orderDetailVO : orderDetailList) {
            for (OrderGoodsVO orderGoodsVO : orderDetailVO.getGoods()) {
                if (orderGoodsVO.getDeliveryStatus() == DeliveryStatus.WAIT_DELIVERY.getCode()) {
                    orderGoodsStatus.put("waitDelivery", orderGoodsStatus.get("waitDelivery") + 1);
                }
                if (orderGoodsVO.getDeliveryStatus() == DeliveryStatus.DELIVERED.getCode()) {
                    orderGoodsStatus.put("delivered", orderGoodsStatus.get("delivered") + 1);
                }
                if (orderGoodsVO.getServiceStatus() == ServiceStatus.REFUNDING.getCode()) {
                    orderGoodsStatus.put("refunding", orderGoodsStatus.get("refunding") + 1);
                }
                if (orderGoodsVO.getServiceStatus() == ServiceStatus.REFUNDED.getCode()) {
                    orderGoodsStatus.put("refunded", orderGoodsStatus.get("refunded") + 1);
                }
            }
        }

        //判断状态数量是否为0，为零的不返回
        Iterator<Map.Entry<String, Integer>> it = orderGoodsStatus.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            if (0 == entry.getValue())
                it.remove();
        }
        return orderGoodsStatus;
    }

    /**
     * 退款详情
     *
     * @param orderGoodsId 订单商品ID
     * @return
     */
    @Override
    public List<RefundDetailVO> getRefundDetail(Long orderGoodsId) {
        // 校验退款订单ID
        checkOrderGoodsId(orderGoodsId);

        List<RefundDetailVO> refundDetailVOList = new ArrayList<>();

        // 根据订单商品ID查询退款订单信息
        List<ShopOrderRefund> refundList = getRefundByOrderGoodsId(orderGoodsId);

        for (ShopOrderRefund refund : refundList) {
            RefundDetailVO refundDetailVO = new RefundDetailVO();
            ShopOrder shopOrder = shopOrderMapper.selectById(refund.getOrderId());
            refundDetailVO.setOrderNo(shopOrder.getOrderNo()); //订单编号
            refundDetailVO.setPhone(refund.getPhone());     //联系电话
            refundDetailVO.setRefundId(refund.getId());
            refundDetailVO.setRefundOrderNo(refund.getRefundOrderNo());
            refundDetailVO.setRefundRemark(refund.getRefundRemark());
            refundDetailVO.setCreatedTime(DateUtils.convertDateFormat(refund.getCreatedTime()));            // 申请时间
            refundDetailVO.setRefundType(refund.getType());
            refundDetailVO.setGoodsNum(refund.getGoodsNum());
            refundDetailVO.setAmount(AmountFormatUtil.amountFormat(refund.getRefundAmount()));
            refundDetailVO.setExpressFee(AmountFormatUtil.amountFormat(refund.getExpressFee()));
            refundDetailVO.setReason(ConstantFactory.me().getDictsByName("退款原因", refund.getReasonId()));
            refundDetailVO.setImgList(ImageUtil.setImageStrURL2List(refund.getImgs()));
            refundDetailVO.setRefundStatus(refund.getStatus());
            refundDetailVO.setReviewRemark(refund.getReviewRemark());
            refundDetailVO.setReviewTime(DateUtils.convertDateFormat(refund.getReviewTime()));              // 审核时间

            refundDetailVO.setRevokeReason(refund.getRevokeReason());
            refundDetailVO.setRevokeTime(DateUtils.convertDateFormat(refund.getRevokeTime()));              // 撤销时间
            refundDetailVO.setExpectedTime(DateUtils.convertDateFormat(refund.getExpectedTime()));          // 退款时间（预计到账时间）

            // 查询商家电话
            refundDetailVO.setShopPhone(shopMapper.selectById(refund.getShopId()).getPhone());

            // 查询订单商品信息
            ShopOrderGoods orderGoods = shopOrderGoodsMapper.selectById(refund.getOrderGoodsId());
            if (null == orderGoods) {
                throw new BussinessException(BizExceptionEnum.SHOP_ORDER_GOODS_IS_NOT_EXIST);
            }

            refundDetailVO.setDeliveryStatus(orderGoods.getDeliveryStatus());
            refundDetailVO.setServiceStatus(orderGoods.getServiceStatus());

            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setGoodsId(orderGoods.getGoodsId());                                                    // 商品ID
            goodsVO.setGoodsName(orderGoods.getGoodsName());                                                // 商品名称
            goodsVO.setGoodsImg(ImageUtil.setImageURL(orderGoods.getGoodsImg()));                           // 商品图片
            goodsVO.setGoodsSpecs(orderGoods.getGoodsSpecs());                                              // 商品规格
            goodsVO.setNum(orderGoods.getNums());                                                           // 购买数量
            refundDetailVO.setGoods(goodsVO);

            // 定义计算截止时间相关参数
            Date userApplyTime = refund.getCreatedTime();           // 用户申请时间
            Date shopReviewTime = refund.getReviewTime();           // 店铺审核时间
            Date userDeliveryTime = null;                           // 用户发货时间

            // 退款类型为退货退款，查询退货物流信息
            if (RefundType.AMOUNT_GOODS.getCode() == refund.getType()) {
                ShopOrderRefundLogistics refundLogistics = getLogisticsByRefundId(refund.getId());
                if (null != refundLogistics) {
                    RefundLogisticsVO logisticsVO = new RefundLogisticsVO();
                    BeanUtils.copyProperties(refundLogistics, logisticsVO);

                    logisticsVO.setRefundLogisticsId(refundLogistics.getId());
                    logisticsVO.setImgList(ImageUtil.setImageStrURL2List(refundLogistics.getImgs()));

                    // 用户发货时间
                    logisticsVO.setDeliveryTime(DateUtils.convertDateFormat(refundLogistics.getDeliveryTime()));

                    // 店铺收货时间
                    logisticsVO.setReceiveTime(DateUtils.convertDateFormat(refundLogistics.getReceiveTime()));

                    // 拒收时间
                    logisticsVO.setRejectTime(DateUtils.convertDateFormat(refundLogistics.getRejectTime()));

                    refundDetailVO.setLogistics(logisticsVO);

                    // 用户收货时间，计算截止时间使用
                    userDeliveryTime = refundLogistics.getDeliveryTime();
                }
            }

            // 计算退款截止时间：x天x时x分
            refundDetailVO.setOffTime(caluateOffTime(refund.getStatus(), userApplyTime, shopReviewTime, userDeliveryTime));

            // 用户已申请次数
            refundDetailVO.setApplyNum(getApplyRefundNum(orderGoodsId));
            refundDetailVOList.add(refundDetailVO);
        }

        return refundDetailVOList;
    }

    /**
     * 根据退款订单ID查询退货物流信息
     *
     * @param refundId 退款订单ID
     * @return
     */
    private ShopOrderRefundLogistics getLogisticsByRefundId(Long refundId) {
        ShopOrderRefundLogistics refundLogistics = new ShopOrderRefundLogistics();
        refundLogistics.setRefundId(refundId);
        refundLogistics.setIsDeleted(IsDeleted.NORMAL.getCode());
        refundLogistics = shopOrderRefundLogisticsMapper.selectOne(refundLogistics);
        return refundLogistics;
    }

    /**
     * 校验订单商品ID
     *
     * @param orderGoodsId 订单商品ID
     * @return
     */
    private ShopOrderGoods checkOrderGoodsId(Long orderGoodsId) {
        if (null == orderGoodsId) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_GOODS_ID_IS_NULL);
        }
        // 查询订单商品信息
        ShopOrderGoods orderGoods = shopOrderGoodsMapper.selectById(orderGoodsId);
        if (null == orderGoods) {
            throw new BussinessException(BizExceptionEnum.SHOP_ORDER_GOODS_IS_NOT_EXIST);
        }
        return orderGoods;
    }

    /**
     * 获取订单运费
     *
     * @param orderId      订单ID
     * @param orderGoodsId 订单商品ID
     * @return
     */
//    private BigDecimal getOrderExpressFee(Long orderId, Long orderGoodsId) {
//        if (checkGoodsIsLastService(orderId, orderGoodsId)) {
//            // 不存在其他无售后的订单商品，返回订单运费
//            ShopOrder order = shopOrderMapper.selectById(orderId);
//            return null == order ? BigDecimal.ZERO : order.getExpressFee();
//        }
//        return BigDecimal.ZERO;
//    }

    /**
     * 校验该商品在订单中是否为最后一件售后商品
     *
     * @param orderId      订单ID
     * @param orderGoodsId 订单商品ID
     * @return
     */
    @Override
    public Boolean checkGoodsIsLastService(Long orderId, Long orderGoodsId) {
        List<ShopOrderGoods> orderGoodsList = shopOrderGoodsMapper.selectList(
                new EntityWrapper<ShopOrderGoods>()
                        .eq("order_id", orderId)
                        .in("service_status", new Integer[]{ServiceStatus.NO_SERVICE.getCode(), ServiceStatus.REFUND_FAIL.getCode()})
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .ne("id", orderGoodsId));
        if (null == orderGoodsList || 0 == orderGoodsList.size()) {
            // 不存在其他无售后的订单商品，返回true
            return true;
        }
        return false;
    }

    /**
     * 根据订单商品ID查询退款订单信息
     *
     * @param orderGoodsId 订单商品ID
     * @return
     */
    private List<ShopOrderRefund> getRefundByOrderGoodsId(Long orderGoodsId) {
        List<ShopOrderRefund> orderRefundList = shopOrderRefundMapper.selectList(
                new EntityWrapper<ShopOrderRefund>()
                        .eq("order_goods_id", orderGoodsId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .orderBy("created_time", false));
        if (null == orderRefundList || 0 == orderRefundList.size()) {
            throw new BussinessException(BizExceptionEnum.REFUND_ORDER_IS_NOT_EXIST);
        }
        return orderRefundList;
    }

    /**
     * 根据order获取店铺订单结算表信息
     *
     * @param orderId 订单ID
     * @return
     */
    private ShopOrderSettlement getOrderSettlementByOrderId(Long orderId) {
        List<ShopOrderSettlement> shopOrderSettlementList = shopOrderSettlementMapper.selectList(
                new EntityWrapper<ShopOrderSettlement>()
                        .eq("order_id", orderId)
                        .orderBy("created_time", false));
        if (null == shopOrderSettlementList || 0 == shopOrderSettlementList.size()) {
            throw new BussinessException(BizExceptionEnum.ORDER_SETTLEMENT_IS_NOT_EXIST);
        }
        return shopOrderSettlementList.get(0);

    }

    /**
     * 计算退款处理截止时间
     *
     * @param refundStatus     退款状态
     * @param userApplyTime    用户申请时间
     * @param shopReviewTime   店铺审核时间
     * @param userDeliveryTime 用户发货时间
     * @return
     */
    private String caluateOffTime(Integer refundStatus, Date userApplyTime, Date shopReviewTime, Date userDeliveryTime) {
        // 退款状态不为待审核和审核通过状态，不计算截止时间
        if (RefundStatus.WAIT_REVIEW.getCode() != refundStatus && RefundStatus.PASS.getCode() != refundStatus) {
            return null;
        }
        Date fetureTime = null;
        if (null != userDeliveryTime) {
            fetureTime = DateUtils.getFetureTime(userDeliveryTime, Const.HANDLE_DAY_LIMIT);
        } else if (null != shopReviewTime) {
            fetureTime = DateUtils.getFetureTime(shopReviewTime, Const.HANDLE_DAY_LIMIT);
        } else if (null != userApplyTime) {
            fetureTime = DateUtils.getFetureTime(userApplyTime, Const.HANDLE_DAY_LIMIT);
        }
        return DateUtils.getDateDiff(new Date(), fetureTime);
    }

    /**
     * 查询用户已申请次数
     *
     * @param orderGoodsId 订单商品ID
     * @return
     */
    private Integer getApplyRefundNum(Long orderGoodsId) {
        // 查询售后信息，申请次数不得超过5次
        List<ShopOrderRefund> refundList = shopOrderRefundMapper.selectList(
                new EntityWrapper<ShopOrderRefund>()
                        .eq("order_goods_id", orderGoodsId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        return null != refundList ? refundList.size() : 0;
    }

    /**
     * 订单评价列表
     *
     * @param page             分页信息
     * @param orderNo          订单编号
     * @param goodsName        商品名称
     * @param evaluateWay      评价方式：0-全部；1-默认好评；2-用户自评
     * @param goodsScore       商品星级查询 1-一星；2-二星；...；5-五星
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd   下单时间查询终止时间
     * @return
     */
    @Override
    public List<EvaluateDetailVO> listOrderEvaluate(Page<EvaluateDetailVO> page, String orderNo, String goodsName, Integer evaluateWay,
                                                    Integer goodsScore, String createdTimeStart, String createdTimeEnd) {

        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        if (ToolUtil.isEmpty(shopId)) {
            throw new BussinessException(BizExceptionEnum.NO_THIS_USER);
        }

        List<EvaluateDetailVO> evaluateDetailVOS = shopOrderDao.listOrderEvaluate(page, shopId, orderNo, goodsName,
                evaluateWay, goodsScore, createdTimeStart, createdTimeEnd);

        for (EvaluateDetailVO evaluateDetailVO : evaluateDetailVOS) {
            for (Map<String, Object> map : evaluateDetailVO.getEvalGoodsList()) {
                if (!map.isEmpty()) {
                    if (map.containsKey("goodsImg")) {
                        map.put("goodsImg", ImageUtil.setImageURL(map.get("goodsImg").toString()));
                    }
                    if (map.containsKey("imgs")) {
                        map.put("imgs", ImageUtil.setImageStrURL2List(map.get("imgs").toString()));
                    }
                    map.remove("serviceScore");
                    map.remove("expressScore");
                }
            }
        }
        return evaluateDetailVOS;
    }

    /**
     * 店铺回复
     *
     * @param goodsEvaluateId 商品评价ID
     * @param replyContent    回复内容
     * @return
     */
    @Override
    public void shopReply(Long goodsEvaluateId, String replyContent) {
        // 登录用户ID
        Long userId = ShiroKit.getUser().getId();

        GoodsEvaluate goodsEvaluate = new GoodsEvaluate();
        goodsEvaluate.setId(goodsEvaluateId);
        goodsEvaluate.setReplyTime(new Date());
        goodsEvaluate.setShopReply(replyContent);
        goodsEvaluate.setModifiedTime(new Date());
        goodsEvaluate.setModifiedUser(userId);
        goodsEvaluate.updateById();
    }

}
