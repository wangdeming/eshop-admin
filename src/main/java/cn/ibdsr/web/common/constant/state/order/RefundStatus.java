package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 订单商品退款订单状态枚举类
 * @Version V1.0
 * @CreateDate 2019-04-02 13:44:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-02 13:44:22    XuZhipeng               类说明
 *
 */
public enum RefundStatus {

    NULL(-1, ""),
    WAIT_REVIEW(1, "待审核"),
    PASS(2, "审核通过"),
    REFUSE(3, "审核不通过"),
    SUCCESS(4, "退款成功"),
    FAIL(5, "退款失败"),
    REVOKE(6, "用户撤销"),
    AUTO_REVOKE(7, "未填写退货物流，自动撤销"),
    CLOSED(8, "售后订单已关闭"),                   // 商家拒收
    ;

    int code;
    String message;

    RefundStatus(int code, String message) {
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
            for (RefundStatus ms : RefundStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}


