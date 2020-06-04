package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum RoomSettingStatus {
    OPEN(1, "开"), CLOSED(0, "关");

    int code;
    String message;

    RoomSettingStatus(int code, String message) {
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
            for (RoomSettingStatus roomSettingStatus :RoomSettingStatus.values()) {
                if (roomSettingStatus.getCode() == value) {
                    return roomSettingStatus.getMessage();
                }
            }
            return "";
        }
    }
}
