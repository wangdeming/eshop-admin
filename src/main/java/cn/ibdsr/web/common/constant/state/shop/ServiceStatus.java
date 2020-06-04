package cn.ibdsr.web.common.constant.state.shop;

/**
 * @Description 店铺订单商品售后状态枚举类
 * @Version V1.0
 * @CreateDate 2019-03-20 08:31:22
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-03-20 08:31:22    Wujiayun               类说明
 */
public enum ServiceStatus {
//1-无售后；2-退款中；3-已退款；4-退款失败（商家拒绝）；

    NULL(-1, ""),
    NO_SERVICE(1, "无售后"),
    REFUNDING(2, "退款中"),
    REFUNDED(3, "已退款"),
    REFUND_FAIL(4, "退款失败"),
    ;

    int code;
    String message;

    ServiceStatus(int code, String message) {
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
            for (ServiceStatus ms : ServiceStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
