package cn.ibdsr.web.common.constant.state.shop;

/**
 * 店铺状态
 *
 * @author XuZhipeng
 * @Date 2019-02-21 09:54:13
 */
public enum ShopStatus {

    UNACCOUNT(1, "未开通账号"), OFFSHELF(2, "下架"), NORMAL(3, "正常营业");

    int code;
    String message;

    ShopStatus(int code, String message) {
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
            for (ShopStatus ms : ShopStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
