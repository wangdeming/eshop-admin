/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.common.constant.state.shop;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/29 16:50
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/29      Zhujingrui               类说明
 *
 */
public enum ShopApplyStatus {

    YES(1, "已申请"), NO(0, "未申请");

    int code;
    String message;

    ShopApplyStatus(int code, String message) {
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
            for (ShopApplyStatus shopApplyStatus : ShopApplyStatus.values()) {
                if (shopApplyStatus.getCode() == value) {
                    return shopApplyStatus.getMessage();
                }
            }
            return "";
        }
    }
}
