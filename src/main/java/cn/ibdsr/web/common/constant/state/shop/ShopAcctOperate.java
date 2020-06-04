package cn.ibdsr.web.common.constant.state.shop;

/**
 * 店铺账号操作
 *
 * @author XuZhipeng
 * @Date 2019-02-26 09:54:13
 */
public enum ShopAcctOperate {
    FREEZE(1, "冻结"),
    UNFREEZE(2, "解冻"),
    NULL(0, "其他");

    int code;
    String message;

    ShopAcctOperate(int code, String message) {
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
            for (ShopAcctOperate ms : ShopAcctOperate.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "其他";
        }
    }

}
