package cn.ibdsr.web.common.constant.state.hotel;

/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */


public enum WindowType {
    NO_WINDOW(0, "无窗"), HAVE_WINDOW(1, "有窗");

    int code;
    String message;

    WindowType(int code, String message) {
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
            for (WindowType windowType : WindowType.values()) {
                if (windowType.getCode() == value) {
                    return windowType.getMessage();
                }
            }
            return "";
        }
    }
}
