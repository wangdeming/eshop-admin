package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum CancelType {

    CANNOT_CANCEL(0, "不可取消"), FREE_CANCEL(1, "免费取消");

    int code;
    String message;

    CancelType(int code, String message) {
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
            for (CancelType cancelType : CancelType.values()) {
                if (cancelType.getCode() == value) {
                    return cancelType.getMessage();
                }
            }
            return "";
        }
    }
}
