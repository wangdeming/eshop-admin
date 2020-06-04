package cn.ibdsr.web.common.constant.state.shop;

/**
 * @Description 店铺订单商品发货状态枚举类
 * @Version V1.0
 * @CreateDate 2019-03-20 08:31:22
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-03-20 08:31:22    Wujiayun               类说明
 */
public enum DeliveryStatus {

    NULL(-1, ""),
    WAIT_DELIVERY(1, "待发货"),
    DELIVERED(2, "已发货"),
    RECEIVED(3, "已收货"),
    REFUSE_RECEIVE(4, "拒收"),
    ;

    int code;
    String message;

    DeliveryStatus(int code, String message) {
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
            for (DeliveryStatus ms : DeliveryStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
