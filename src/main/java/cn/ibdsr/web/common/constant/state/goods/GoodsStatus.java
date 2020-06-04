package cn.ibdsr.web.common.constant.state.goods;

/**
 * 商品状态
 *
 * @author XuZhipeng
 * @Date 2019-03-05 09:54:13
 */
public enum GoodsStatus {

    NORMAL(1, "正常"), OFFSHELF(2, "下架");

    int code;
    String message;

    GoodsStatus(int code, String message) {
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
            for (GoodsStatus ms : GoodsStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
