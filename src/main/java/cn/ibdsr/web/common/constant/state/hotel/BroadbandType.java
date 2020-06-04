package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum BroadbandType {

    NO_BROADBAND(0, "无宽带"), WIRED(1, "有线宽带"), WIFI(2, "WiFi"), WIRED_WIFI(3, "WIFI、有线宽带");

    int code;
    String message;

    BroadbandType(int code, String message) {
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
            for (BroadbandType broadbandType : BroadbandType.values()) {
                if (broadbandType.getCode() == value) {
                    return broadbandType.getMessage();
                }
            }
            return "";
        }
    }
}
