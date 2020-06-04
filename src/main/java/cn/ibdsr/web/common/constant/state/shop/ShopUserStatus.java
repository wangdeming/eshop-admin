package cn.ibdsr.web.common.constant.state.shop;

/**
 * 店铺用户状态
 *
 * @author XuZhipeng
 * @Date 2019-02-21 09:54:13
 */
public enum ShopUserStatus {

    NOACTIVE(1, "未激活"), OK(2, "正常"), FREEZED(3, "冻结");

    int code;
    String message;

    ShopUserStatus(int code, String message) {
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
            for (ShopUserStatus ms : ShopUserStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
