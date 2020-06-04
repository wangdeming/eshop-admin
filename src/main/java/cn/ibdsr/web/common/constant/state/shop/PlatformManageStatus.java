package cn.ibdsr.web.common.constant.state.shop;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum PlatformManageStatus {
    ONSHELF(1, "未下架"), OFFSHELF(0, "已下架");

    int code;
    String message;

    PlatformManageStatus(int code, String message) {
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
            for (PlatformManageStatus platformManageStatus : PlatformManageStatus.values()) {
                if (platformManageStatus.getCode() == value) {
                    return platformManageStatus.getMessage();
                }
            }
            return "";
        }
    }
}
