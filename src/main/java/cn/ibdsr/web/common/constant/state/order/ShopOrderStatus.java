package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 店铺订单状态枚举类
 * @Version V1.0
 * @CreateDate 2019-03-20 08:31:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-03-20 08:31:22    XuZhipeng               类说明
 *
 */
public enum ShopOrderStatus {

    NULL(-1, ""),
    WAIT_PAY(1, "待付款"),
    WAIT_DELIVERY(2, "待发货"),
    WAIT_RECEIVE(3, "待收货"),
    RECEIVED(4, "已收货"),
    CANCEL(5, "已取消"),
    SERVICE(6, "退款中"),
    CLOSED(7, "交易关闭"),
    COMPLETED(8, "已完成"),        // 订单结算
    ;

    int code;
    String message;

    ShopOrderStatus(int code, String message) {
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
            for (ShopOrderStatus ms : ShopOrderStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
