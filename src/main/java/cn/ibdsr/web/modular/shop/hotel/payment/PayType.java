package cn.ibdsr.web.modular.shop.hotel.payment;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.modular.shop.hotel.service.PayService;

/**
 * 支付方式，1为微信，2为支付宝，3为银联
 * 原路退回 退款就是 支付方式的原路
 *
 * @Author xjc
 */
public enum PayType implements PayService {
    WEIXIN(1, "微信", Boolean.TRUE) {
        @Override
        public Object getChannel(Payment payment) throws Exception {
            return new WXPayUtils().payment(payment).getPayMap();
        }

        @Override
        public Object refundChannel(Payment payment) throws Exception {
            return new WXPayUtils().refund(payment).getPayMap();
        }
    },
    ALIPAY(2, "支付宝", Boolean.TRUE) {
        @Override
        public Object getChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.ORDER_PAY_IS_NOT_OPEN);
        }

        @Override
        public Object refundChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.RERUND_PAY_IS_NOT_OPEN);
        }
    },
    UNIONPAY(3, "银联", Boolean.FALSE) {
        @Override
        public Object getChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.ORDER_PAY_IS_NOT_OPEN);
        }

        @Override
        public Object refundChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.RERUND_PAY_IS_NOT_OPEN);
        }
    },
    OTHER(4, "其他", Boolean.FALSE) {
        @Override
        public Object getChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.ORDER_PAY_IS_NOT_OPEN);
        }

        @Override
        public Object refundChannel(Payment payment) {
            throw new BussinessException(BizExceptionEnum.RERUND_PAY_IS_NOT_OPEN);
        }
    },
    ;
    private Integer code;
    private String message;
    private Boolean isOpen;

    PayType(Integer code, String message, Boolean isOpen) {
        this.code = code;
        this.message = message;
        this.isOpen = isOpen;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public static PayType valueOf(Integer code) {

        if (code == null) {
            return null;
        }
        for (PayType payType : values()) {
            if (code == payType.getCode()) {
                return payType;
            }
        }// end for
        /**如果为空，怎么没有该支付类型*/
        return null;
    }

}
