package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum HotelOrderStatus {
    NULL(-1, ""),
    WAIT_PAY(1, "待付款"),
    WAIT_CONFIRM(2, "待确认"),
    WAIT_USE(3, "待使用"),
    USED(4, "已消费"),
    CANCEL(5, "已取消"),
    FINISHED(6, "已完成"),
    EXPIRED(7, "已过期"),
    ;

    int code;
    String message;

    HotelOrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer value) {
        if (value == null) {
            return "";
        } else {
            for (HotelOrderStatus hotelOrderStatus : HotelOrderStatus.values()) {
                if (hotelOrderStatus.getCode() == value) {
                    return hotelOrderStatus.getMessage();
                }
            }
            return "";
        }
    }
}
