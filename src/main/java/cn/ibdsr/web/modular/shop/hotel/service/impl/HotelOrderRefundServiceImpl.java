/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.web.common.constant.state.PaymentStatus;
import cn.ibdsr.web.common.constant.state.cash.PlatformBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.hotel.PaymentReturnCode;
import cn.ibdsr.web.common.constant.state.order.OrderCashTransSrc;
import cn.ibdsr.web.common.constant.state.order.OrderCashType;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.HotelOrderMapper;
import cn.ibdsr.web.common.persistence.dao.PaymentMapper;
import cn.ibdsr.web.common.persistence.model.HotelOrder;
import cn.ibdsr.web.common.persistence.model.HotelOrderRefund;
import cn.ibdsr.web.core.util.OrderUtils;
import cn.ibdsr.web.modular.platform.cash.service.ICashChangeService;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import cn.ibdsr.web.modular.shop.hotel.payment.Payment;
import cn.ibdsr.web.modular.shop.hotel.payment.PaymentResult;
import cn.ibdsr.web.modular.shop.hotel.payment.WXPayUtils;
import cn.ibdsr.web.modular.shop.hotel.service.HotelOrderRefundService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderRefundService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/7/9 15:23
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/7/9      Zhujingrui               类说明
 *
 */
@Service
public class HotelOrderRefundServiceImpl implements HotelOrderRefundService{

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ICashTransferService cashTransferService;

    @Autowired
    private ICashChangeService cashChangeService;

    @Override
    public void refund(String hotelOrderId) throws Exception {
        HotelOrder hotelOrder = hotelOrderMapper.selectById(hotelOrderId);
        if (hotelOrder == null) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }
        cn.ibdsr.web.common.persistence.model.Payment param = new cn.ibdsr.web.common.persistence.model.Payment();
        param.setOrderIds(hotelOrderId);
        cn.ibdsr.web.common.persistence.model.Payment pay = paymentMapper.selectOne(param);
        if (pay == null || pay.getStatus() != PaymentStatus.SUCCESS.getCode()) {
            throw new BussinessException(BizExceptionEnum.DATA_ERROR);
        }
        Long userId = ShiroKit.getUser().getId();
        Date now = new Date();
        HotelOrderRefund hotelOrderRefund = new HotelOrderRefund();
        hotelOrderRefund.setShopId(hotelOrder.getShopId());
        hotelOrderRefund.setOrderId(hotelOrder.getId());
        hotelOrderRefund.setRoomId(hotelOrder.getRoomId());
        hotelOrderRefund.setRefundOrderNo(OrderUtils.getOrderNoByUUId(hotelOrder.getId()));
        hotelOrderRefund.setRefundAmount(pay.getPayAmount());
        hotelOrderRefund.setCreatedUser(userId);
        hotelOrderRefund.setCreatedTime(now);

        //申请退款
        Payment payment = new Payment();
        payment.setOutTradeCode(pay.getActualOutTradeNo());
        payment.setAmount(hotelOrderRefund.getRefundAmount());
        payment.setTotalAmount(hotelOrderRefund.getRefundAmount());
        payment.setOutRefundNo(hotelOrderRefund.getRefundOrderNo());
        PaymentResult result = (new WXPayUtils()).refund(payment);
        if (!StringUtils.equals(result.getCode(), PaymentReturnCode.SUCCESS.getCode())) {
            throw new BussinessException(BizExceptionEnum.REFUND_FAIL);
        }

        hotelOrderRefund.setOriginalRefundId(result.getOutBizNo());
        hotelOrderRefund.setOriginalString(result.getOriginalString());
        hotelOrderRefund.setExpectedTime(new Date());
        hotelOrderRefund.insert();

        // 售后退款资金变动：待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
        // 待到账金额-
        cashChangeService.debitOrderCash(hotelOrder.getShopId(), hotelOrderId, hotelOrder.getTotalAmount(), OrderCashType.INCOME_AMOUNT.getCode(), OrderCashTransSrc.REFUND.getCode(), null, ShopType.HOTEL.getCode());

        // 平台余额-
        cashChangeService.debitPlatformBalance(hotelOrder.getTotalAmount(), PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode(), hotelOrder.getId(), null);

    }

}
