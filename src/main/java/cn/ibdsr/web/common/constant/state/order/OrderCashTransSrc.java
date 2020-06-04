package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 待入账，待出账金额类型
 * @Version V1.0
 * @CreateDate 2019-04-26 10:26:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-26 10:26:22    XuZhipeng               类说明
 *
 */
public enum OrderCashTransSrc {

    NULL(-1, ""),
    PAYMENT(1, "用户已付款"),                // 待出账金额+（shop_order_cash_flow）；平台余额+（platform_balance_flow）
    REFUND(2, "用户退款"),                  // 待出账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
    CONFIRM_RECEIVE(3, "用户已收货"),        // 待出账金额-、待到账金额+（shop_order_cash_flow）
    SERVICE_REFUND(4, "用户售后退款"),        // 待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
    ORDER_SETTLE(5, "订单已结算"),           // 待到账金额-（shop_order_cash_flow）；店铺余额+（shop_balance_flow）
    ;

    int code;
    String message;

    OrderCashTransSrc(int code, String message) {
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


