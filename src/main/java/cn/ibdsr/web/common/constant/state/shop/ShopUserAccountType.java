package cn.ibdsr.web.common.constant.state.shop;

public enum ShopUserAccountType {

    PARENT(1, "主账号"), SUB(2, "子账号");

    int code;

    String message;

    ShopUserAccountType(int code, String message) {
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
            for (ShopUserAccountType ms : ShopUserAccountType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
