package cn.ibdsr.web.common.constant.state.goods;

/**
 * 商品类别等级
 *
 * @author XuZhipeng
 * @Date 2019-02-25 09:54:13
 */
public enum CategoryLevel {
    LEVEL1(1, "级别1"), LEVEL2(2, "级别2");

    int code;
    String message;

    CategoryLevel(int code, String message) {
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
            for (CategoryLevel ms : CategoryLevel.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
