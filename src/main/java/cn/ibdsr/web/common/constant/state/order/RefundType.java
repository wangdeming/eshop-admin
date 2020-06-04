package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 订单商品退款订单类型枚举类
 * @Version V1.0
 * @CreateDate 2019-04-02 13:44:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-02 13:44:22    XuZhipeng               类说明
 *
 */
public enum RefundType {

    NULL(-1, ""),
    AMOUNT(1, "仅退款"),
    AMOUNT_GOODS(2, "退款退货"),
    ;

    int code;
    String message;

    RefundType(int code, String message) {
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
            for (RefundType ms : RefundType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}


