package cn.ibdsr.web.common.constant.state;

/**
 * @Description description
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/18 13:14
 * @Modifed by
 */
public enum PlatformType {
    PLATFORM(1, "平台"), SHOP(2, "店铺"), CUSTOMER(3, "消费用户");
    int code;
    String message;

    PlatformType(int code, String message) {
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
            for (PlatformType ms : PlatformType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
