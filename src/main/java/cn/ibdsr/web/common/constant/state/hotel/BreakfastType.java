package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum BreakfastType {

    NOTHING(0, "无早餐"), SINGLE(1, "单人早餐"), DOUBLE(1, "双人早餐");

    int code;
    String message;

    BreakfastType(int code, String message) {
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
            for (BreakfastType breakfastType : BreakfastType.values()) {
                if (breakfastType.getCode() == value) {
                    return breakfastType.getMessage();
                }
            }
            return "";
        }
    }
}
