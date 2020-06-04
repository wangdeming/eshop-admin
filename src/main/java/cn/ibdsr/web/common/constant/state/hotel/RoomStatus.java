package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum RoomStatus {
    ONSHELF(1, "销售中"), OFFSHELF(0, "未上架");

    int code;
    String message;

    RoomStatus(int code, String message) {
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
            for (RoomStatus roomStatus : RoomStatus.values()) {
                if (roomStatus.getCode() == value) {
                    return roomStatus.getMessage();
                }
            }
            return "";
        }
    }
}
