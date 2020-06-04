package cn.ibdsr.web.modular.shop.order.service.impl;

import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.IsDefault;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.PaymentStatus;
import cn.ibdsr.web.common.constant.state.cash.PlatformBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.order.OrderCashTransSrc;
import cn.ibdsr.web.common.constant.state.order.OrderCashType;
import cn.ibdsr.web.common.constant.state.order.RefundStatus;
import cn.ibdsr.web.common.constant.state.order.RefundType;
import cn.ibdsr.web.common.constant.state.order.ShopOrderStatus;
import cn.ibdsr.web.common.constant.state.shop.DeliveryStatus;
import cn.ibdsr.web.common.constant.state.shop.ServiceStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsSkuMapper;
import cn.ibdsr.web.common.persistence.dao.PaymentMapper;
import cn.ibdsr.web.common.persistence.dao.ShopDeliveryAddressMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderGoodsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundLogisticsMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderRefundMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.GoodsSku;
import cn.ibdsr.web.common.persistence.model.Payment;
import cn.ibdsr.web.common.persistence.model.ShopDeliveryAddress;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderGoods;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefundLogistics;
import cn.ibdsr.web.core.mq.rabbitmq.MessageSenderTask;
import cn.ibdsr.web.core.util.CommonUtils;
import cn.ibdsr.web.core.util.WechatPayUtils;
import cn.ibdsr.web.modular.platform.cash.service.ICashChangeService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderRefundService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 订单退款
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/3 17:12
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/3 xujincai init
 */
@Service
public class ShopOrderRefundServiceImpl implements ShopOrderRefundService {

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderGoodsMapper shopOrderGoodsMapper;

    @Autowired
    private ShopOrderRefundMapper shopOrderRefundMapper;

    @Autowired
    private ShopOrderRefundLogisticsMapper shopOrderRefundLogisticsMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ShopDeliveryAddressMapper shopDeliveryAddressMapper;

    @Autowired
    private ICashChangeService cashChangeService;

    @Autowired
    private MessageSenderTask messageSenderTask;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 获取支付订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public Payment payment(Long orderId) {
        Wrapper<Payment> paymentWrapper = new EntityWrapper<>();
        paymentWrapper.eq("status", PaymentStatus.SUCCESS.getCode());
        paymentWrapper.where("(INSTR( CONCAT(',',order_ids,','), '," + orderId + ",' ) OR order_ids='" + orderId + "')");
        List<Payment> paymentList = paymentMapper.selectList(paymentWrapper);
        if (paymentList.size() == 0) {
            throw new BussinessException(BizExceptionEnum.ORDER_REFUND_NO_PAY_INFO);
        }
        if (paymentList.size() > 1) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }
        return paymentList.get(0);
    }

    /**
     * 执行退款
     *
     * @param shopOrderRefund
     * @param payment
     */
    @Override
    public Map<String, String> refund(ShopOrderRefund shopOrderRefund, Payment payment) throws Exception {
        Map<String, String> reqData = new HashMap<>();
        reqData.put("appid", ConstantFactory.me().getWXpayProperties().getAppid());
        reqData.put("mch_id", payment.getMerchantId());
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        reqData.put("sign_type", WXPayConstants.SignType.MD5.name());
        reqData.put("out_trade_no", payment.getActualOutTradeNo());
        String refundOrderNo = CommonUtils.getOrderCode();
        reqData.put("out_refund_no", refundOrderNo);
        reqData.put("total_fee", payment.getPayAmount().toPlainString());
        reqData.put("refund_fee", shopOrderRefund.getRefundAmount().toPlainString());
        return (new WechatPayUtils()).refund(reqData);
    }

    /**
     * 库存变更，枷锁，
     *
     * @param goodsId    商品ID
     * @param goodsSkuId 商品规格ID
     * @param number     数量，库存增加为正数，库存扣除为负数
     */
    @Override
    public synchronized void changeGoodsStock(Long goodsId, Long goodsSkuId, int number, ShiroUser user, Date date) {
        Goods param = goodsMapper.selectById(goodsId);
        if (param != null) {
            Goods goods = new Goods();
            goods.setId(goodsId);
            goods.setStock(param.getStock() + number);
            if (user != null) {
                goods.setModifiedUser(user.getId());
            }
            goods.setModifiedTime(date);
            goods.updateById();
        }
        if (goodsSkuId != null) {
            //有规格商品
            GoodsSku skuParam = goodsSkuMapper.selectById(goodsSkuId);
            if (skuParam != null) {
                GoodsSku goodsSku = new GoodsSku();
                goodsSku.setId(goodsSkuId);
                goodsSku.setStock(skuParam.getStock() + number);
                if (user != null) {
                    goodsSku.setModifiedUser(user.getId());
                }
                goodsSku.setModifiedTime(date);
                goodsSku.updateById();
            }
        }
    }

    /**
     * 判断是否最后一份商品进行退货处理
     *
     * @param shopOrderRefund
     * @return
     */
    private boolean last(ShopOrderRefund shopOrderRefund) {
        //订单商品总数量
        Integer num = 0;
        //已退货商品数量
        Integer refundNum = 0;

        Wrapper<ShopOrderGoods> shopOrderGoodsWrapper = new EntityWrapper<>();
        shopOrderGoodsWrapper.eq("order_id", shopOrderRefund.getOrderId());
        shopOrderGoodsWrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(shopOrderGoodsWrapper);
        for (ShopOrderGoods shopOrderGoods : shopOrderGoodsList) {
            num = num + shopOrderGoods.getNums();
        }

        Wrapper<ShopOrderRefund> shopOrderRefundWrapper = new EntityWrapper<>();
        shopOrderRefundWrapper.eq("order_id", shopOrderRefund.getOrderId());
        shopOrderRefundWrapper.eq("status", RefundStatus.SUCCESS.getCode());
        shopOrderRefundWrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        shopOrderRefundWrapper.ne("id", shopOrderRefund.getId());
        List<ShopOrderRefund> shopOrderRefundList = shopOrderRefundMapper.selectList(shopOrderRefundWrapper);
        for (ShopOrderRefund orderRefund : shopOrderRefundList) {
            refundNum = refundNum + orderRefund.getGoodsNum();
        }

        if ((shopOrderRefund.getGoodsNum() + refundNum) == num) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 审核通过
     * 1、用户申请后，商家需要在7天内完成审核。否则系统帮助商家审核通过，并退款。（未发货商品售后）(此功能由消息队列调用此service)
     * 2、用户申请后，商家需要在7天内完成审核。否则系统帮助商家审核通过。（已发货商品售后）(此功能由消息队列调用此service)
     * 3、商家主动审核通过
     *
     * @param shopOrderRefundId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public synchronized SuccessMesTip passRefund(Long shopOrderRefundId) throws Exception {
        Date now = new Date();
        ShiroUser user = ShiroKit.getUser();
        ShopOrderRefund shopOrderRefund = shopOrderRefundMapper.selectById(shopOrderRefundId);
        if (shopOrderRefund == null) {
            return null;
        }
        String message = "";
        if (shopOrderRefund.getStatus() == RefundStatus.WAIT_REVIEW.getCode()) {
            //通过并直接退款，状态设置为退款成功
            if (shopOrderRefund.getType() == RefundType.AMOUNT.getCode()) {
                Payment payment = payment(shopOrderRefund.getOrderId());
                Map<String, String> result = refund(shopOrderRefund, payment);
                if (StringUtils.equals(Const.SUCCESS, result.get("return_code")) && StringUtils.equals(Const.SUCCESS, result.get("result_code"))) {
                    ShopOrderRefund refund = new ShopOrderRefund();
                    refund.setId(shopOrderRefund.getId());
                    refund.setStatus(RefundStatus.SUCCESS.getCode());
                    refund.setReviewTime(now);
                    refund.setExpectedTime(now);
                    refund.setOriginalRefundId(result.get("refund_id"));
                    refund.setOriginalString(JSONObject.toJSONString(result));
                    refund.setReviewRemark("商家超时，未审核，系统默认审核通过并退款。");
                    if (user != null) {
                        refund.setModifiedUser(user.getId());
                    }
                    refund.setModifiedTime(now);
                    refund.updateById();

                    ShopOrderGoods shopOrderGoods = new ShopOrderGoods();
                    shopOrderGoods.setId(shopOrderRefund.getOrderGoodsId());
                    shopOrderGoods.setServiceStatus(ServiceStatus.REFUNDED.getCode());
                    if (user != null) {
                        shopOrderGoods.setModifiedUser(user.getId());
                    }
                    shopOrderGoods.setModifiedTime(now);
                    shopOrderGoods.updateById();

                    if (last(shopOrderRefund)) {
                        //该退货是该订单最后一批商品，修改订单为“已关闭”
                        ShopOrder shopOrder = new ShopOrder();
                        shopOrder.setId(shopOrderRefund.getOrderId());
                        shopOrder.setStatus(ShopOrderStatus.CLOSED.getCode());
                        if (user != null) {
                            shopOrder.setModifiedUser(user.getId());
                        }
                        shopOrder.setModifiedTime(now);
                        shopOrder.updateById();
                    }

                    //返回库存
                    List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(new EntityWrapper<ShopOrderGoods>().eq("order_id", shopOrderRefund.getOrderId()).eq("is_deleted", IsDeleted.NORMAL.getCode()));
                    for (ShopOrderGoods r : shopOrderGoodsList) {
                        changeGoodsStock(r.getGoodsId(), r.getSkuId(), r.getNums(), user, now);
                    }

                    //增加资金变动记录，用户退款：待出账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
                    cashChangeService.debitOrderCash(shopOrderRefund.getShopId(), String.valueOf(shopOrderRefund.getOrderId()), shopOrderRefund.getRefundAmount(), OrderCashType.OUTACC_AMOUNT.getCode(), OrderCashTransSrc.REFUND.getCode(), null, ShopType.STORE.getCode());
                    cashChangeService.debitPlatformBalance(shopOrderRefund.getRefundAmount(), PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode(), String.valueOf(shopOrderRefund.getOrderId()), null);

                    message = "已审核通过并成功退款。";

                    // 发送消息：订单7天内无申请售后，自动结算
                    messageSenderTask.sendMsgOfSettleShopOrder(shopOrderRefund.getOrderId());
                }
            }
            //通过，状态设置为审核通过
            if (shopOrderRefund.getType() == RefundType.AMOUNT_GOODS.getCode()) {
                ShopDeliveryAddress param = new ShopDeliveryAddress();
                param.setShopId(shopOrderRefund.getShopId());
                param.setIsDefault(IsDefault.Y.getCode());
                ShopDeliveryAddress address = shopDeliveryAddressMapper.selectOne(param);
                if (address == null) {
                    throw new BussinessException(BizExceptionEnum.DATA_ERROR);
                }
                ShopOrderRefund refund = new ShopOrderRefund();
                refund.setId(shopOrderRefund.getId());
                refund.setStatus(RefundStatus.PASS.getCode());
                refund.setReviewTime(now);
                refund.setReviewRemark("商家超时，未审核，系统默认审核通过。");
                if (user != null) {
                    refund.setModifiedUser(user.getId());
                }
                refund.setModifiedTime(now);
                //修改退款信息
                refund.updateById();

                ShopOrderRefundLogistics shopOrderRefundLogistics = new ShopOrderRefundLogistics();
                shopOrderRefundLogistics.setRefundId(shopOrderRefund.getId());
                shopOrderRefundLogistics.setConsigneeName(address.getConsigneeName());
                shopOrderRefundLogistics.setConsigneePhone(address.getConsigneePhone());
                shopOrderRefundLogistics.setProvince(address.getProvince());
                shopOrderRefundLogistics.setCity(address.getCity());
                shopOrderRefundLogistics.setDistrict(address.getDistrict());
                shopOrderRefundLogistics.setAddress(address.getAddress());
                shopOrderRefundLogistics.setStatus(DeliveryStatus.WAIT_DELIVERY.getCode());
                if (user != null) {
                    shopOrderRefundLogistics.setModifiedUser(user.getId());
                }
                shopOrderRefundLogistics.setCreatedTime(now);
                //插入商家收货信息
                shopOrderRefundLogistics.insert();
                message = "操作成功。";

                messageSenderTask.sendMsgOfRevokeShopOrderRefund(shopOrderRefundId);
            }
        }
        return new SuccessMesTip(message);
    }

    /**
     * 确认收货
     *
     * @param shopOrderRefundId
     * @param shopOrderRefundLogisticsId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public SuccessMesTip confirmReceipt(Long shopOrderRefundId, Long shopOrderRefundLogisticsId) throws Exception {
        ShopOrderRefund shopOrderRefund = shopOrderRefundMapper.selectById(shopOrderRefundId);
        if (shopOrderRefund == null) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }
        Payment payment = payment(shopOrderRefund.getOrderId());
        Map<String, String> result = refund(shopOrderRefund, payment);

        Date now = new Date();
        ShiroUser user = ShiroKit.getUser();
        String message = "";
        if (StringUtils.equals(Const.SUCCESS, result.get("return_code")) && StringUtils.equals(Const.SUCCESS, result.get("result_code"))) {
            ShopOrderRefund refund = new ShopOrderRefund();
            refund.setId(shopOrderRefund.getId());
            refund.setStatus(RefundStatus.SUCCESS.getCode());
            refund.setOriginalRefundId(result.get("refund_id"));
            refund.setExpectedTime(now);
            refund.setOriginalString(JSONObject.toJSONString(result));
            if (user != null) {
                refund.setModifiedUser(user.getId());
            }
            refund.setModifiedTime(now);
            refund.updateById();

            ShopOrderGoods shopOrderGoods = new ShopOrderGoods();
            shopOrderGoods.setId(shopOrderRefund.getOrderGoodsId());
            shopOrderGoods.setServiceStatus(ServiceStatus.REFUNDED.getCode());
            if (user != null) {
                shopOrderGoods.setModifiedUser(user.getId());
            }
            shopOrderGoods.setModifiedTime(now);
            shopOrderGoods.updateById();

            ShopOrderRefundLogistics shopOrderRefundLogistics = new ShopOrderRefundLogistics();
            shopOrderRefundLogistics.setId(shopOrderRefundLogisticsId);
            if (user != null) {
                shopOrderRefundLogistics.setReceiveReason("商家确认收货并退款。");
            } else {
                shopOrderRefundLogistics.setReceiveReason("商家逾期未处理，系统自动确认收货并退款。");
            }
            shopOrderRefundLogistics.setStatus(3);
            if (user != null) {
                shopOrderRefundLogistics.setModifiedUser(user.getId());
            }
            shopOrderRefundLogistics.setModifiedTime(now);
            shopOrderRefundLogistics.updateById();

            if (last(shopOrderRefund)) {
                //该退货是该订单最后一批商品，修改订单为“已关闭”
                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setId(shopOrderRefund.getOrderId());
                shopOrder.setStatus(ShopOrderStatus.CLOSED.getCode());
                if (user != null) {
                    shopOrder.setModifiedUser(user.getId());
                }
                shopOrder.setModifiedTime(now);
                shopOrder.updateById();
            } else {
                Wrapper<ShopOrderGoods> shopOrderGoodsWrapper = new EntityWrapper<>();
                shopOrderGoodsWrapper.eq("order_id", shopOrderRefund.getOrderId());
                shopOrderGoodsWrapper.eq("service_status", ServiceStatus.REFUNDING.getCode());
                shopOrderGoodsWrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
                shopOrderGoodsWrapper.ne("id", shopOrderRefund.getOrderGoodsId());
                List<ShopOrderGoods> shopOrderGoodsList = shopOrderGoodsMapper.selectList(shopOrderGoodsWrapper);
                if (shopOrderGoodsList.size() == 0) {
                    ShopOrder order = shopOrderMapper.selectById(shopOrderRefund.getOrderId());

                    ShopOrder shopOrder = new ShopOrder();
                    shopOrder.setId(shopOrderRefund.getOrderId());
                    shopOrder.setStatus(order.getPreStatus());
                    if (user != null) {
                        shopOrder.setModifiedUser(user.getId());
                    }
                    shopOrder.setModifiedTime(now);
                    shopOrder.updateById();
                }
            }
            message = "已确认收货并成功退款。";
        }

        // 发送消息：订单7天内无申请售后，自动结算
        messageSenderTask.sendMsgOfSettleShopOrder(shopOrderRefund.getOrderId());

        return new SuccessMesTip(message);
    }

    @Override
    @Transactional
    public SuccessTip refuseReceipt(Long shopOrderRefundId, String reason) {
        if (StringUtils.isBlank(reason)) {
            throw new BussinessException(BizExceptionEnum.SHOP_REFUSE_RECEIPT_RESASON_BLANK);
        }

        Date now = new Date();
        ShiroUser user = ShiroKit.getUser();

        ShopOrderRefund refund = shopOrderRefundMapper.selectById(shopOrderRefundId);
        ShopOrderRefund shopOrderRefund = new ShopOrderRefund();
        shopOrderRefund.setId(refund.getId());
        shopOrderRefund.setStatus(RefundStatus.CLOSED.getCode());
        shopOrderRefund.setModifiedUser(user.getId());
        shopOrderRefund.setModifiedTime(now);
        shopOrderRefund.updateById();

        ShopOrderRefundLogistics logisticsParam = new ShopOrderRefundLogistics();
        logisticsParam.setRefundId(shopOrderRefundId);
        logisticsParam.setIsDeleted(IsDeleted.NORMAL.getCode());
        ShopOrderRefundLogistics logistics = shopOrderRefundLogisticsMapper.selectOne(logisticsParam);
        ShopOrderRefundLogistics shopOrderRefundLogistics = new ShopOrderRefundLogistics();
        shopOrderRefundLogistics.setId(logistics.getId());
        shopOrderRefundLogistics.setStatus(DeliveryStatus.REFUSE_RECEIVE.getCode());
        shopOrderRefundLogistics.setRejectReason(reason);
        shopOrderRefundLogistics.setRejectTime(now);
        shopOrderRefundLogistics.setModifiedUser(user.getId());
        shopOrderRefundLogistics.setModifiedTime(now);
        shopOrderRefundLogistics.updateById();

        ShopOrderGoods shopOrderGoods = new ShopOrderGoods();
        shopOrderGoods.setId(refund.getOrderGoodsId());
        shopOrderGoods.setServiceStatus(ServiceStatus.REFUND_FAIL.getCode());
        shopOrderGoods.setModifiedUser(user.getId());
        shopOrderGoods.setModifiedTime(now);
        shopOrderGoods.updateById();

        // 不存在其他无售后的订单商品，修改订单状态
        boolean isLastService = shopOrderService.checkGoodsIsLastService(refund.getOrderId(), refund.getOrderGoodsId());
        if (isLastService) {
            // 查询订单信息
            ShopOrder order = shopOrderMapper.selectById(refund.getOrderId());
            if (null != order) {
                order.setStatus(order.getPreStatus());
                order.setModifiedTime(now);
                order.setModifiedUser(user.getId());
                order.updateById();
            }
        }

        // 发送消息：订单7天内无申请售后，自动结算
        messageSenderTask.sendMsgOfSettleShopOrder(refund.getOrderId());

        return new SuccessTip();
    }

    @Override
    public void revoke(Long shopOrderRefundId) {
        ShopOrderRefund refund = shopOrderRefundMapper.selectById(shopOrderRefundId);
        List<ShopOrderRefundLogistics> shopOrderRefundLogisticsList = shopOrderRefundLogisticsMapper.selectList(new EntityWrapper<ShopOrderRefundLogistics>().eq("refund_id", shopOrderRefundId).isNotNull("express_no"));
        if (refund.getStatus() == RefundStatus.PASS.getCode() && shopOrderRefundLogisticsList.size() == 0) {
            Date now = new Date();
            ShopOrderRefund shopOrderRefund = new ShopOrderRefund();
            shopOrderRefund.setId(shopOrderRefundId);
            shopOrderRefund.setStatus(RefundStatus.AUTO_REVOKE.getCode());
            shopOrderRefund.setModifiedTime(new Date());
            shopOrderRefund.setRevokeReason("超时未填写退货物流");
            shopOrderRefund.setRevokeTime(now);
            shopOrderRefund.updateById();

            // 修改订单商品售后状态
            ShopOrderGoods orderGoods = new ShopOrderGoods();
            orderGoods.setId(refund.getOrderGoodsId());
            orderGoods.setServiceStatus(ServiceStatus.REFUND_FAIL.getCode());
            orderGoods.setModifiedTime(now);
            orderGoods.updateById();

            //如果订单状态为退款中，则修改订单为之前状态，否则不修改订单状态
            ShopOrder order = shopOrderMapper.selectById(refund.getOrderId());
            if (order.getStatus() == ShopOrderStatus.SERVICE.getCode()) {
                ShopOrder shopOrder = new ShopOrder();
                shopOrder.setId(order.getId());
                shopOrder.setStatus(order.getPreStatus());
                shopOrder.setModifiedTime(now);
                shopOrder.updateById();
            }

            // 发送消息：订单7天内无申请售后，自动结算
            messageSenderTask.sendMsgOfSettleShopOrder(refund.getOrderId());
        }
    }

}
