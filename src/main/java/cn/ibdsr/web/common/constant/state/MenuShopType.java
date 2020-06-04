package cn.ibdsr.web.common.constant.state;

/**
 * 店铺类型
 *
 * @author XuZhipeng
 * @Date 2019-02-21 09:54:13
 */
public enum MenuShopType {

    STORE(1, "特产店铺"), HOTEL(2, "酒店店铺"), COMMON(3, "酒店店铺");

    int code;
    String message;

    MenuShopType(int code, String message) {
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
            for (MenuShopType ms : MenuShopType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
