package cn.ibdsr.web.common.constant.state;

/**
 * @Description 延迟任务
 * @Version V1.0
 * @CreateDate 2019-04-25 13:31:22
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 13:31:22    XuZhipeng               类说明
 */
public enum DelayTask {

    SHOP_ORDER_CANCEL(1, "订单未付款自动取消"),
    SHOP_REFUND_PASS(2, "商家7天内未审核的售后订单自动审核通过"),
    SHOP_ORDER_REVOKE(3, "用户未在7天内填写物流信息，售后订单自动撤销"),
    SHOP_CONFIRM_RECEIPT(4, "商家7天后未收货的售后订单自动确认收货"),
    SHOP_ORDER_SETTLE(5, "确认收货并且无售后信息的订单7天后自动结算"),
    //上方为特产商家，下方为酒店
    HOTEL_ORDER_CANCEL(6, "订单未付款自动取消"),
    HOTEL_ORDER_CONFIRM(7, "未确认订单系统自动确认"),
    HOTEL_ORDER_SETTLE(8, "已消费订单7天后自动结算"),
    ;

    int code;
    String message;

    DelayTask(int code, String message) {
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

}
