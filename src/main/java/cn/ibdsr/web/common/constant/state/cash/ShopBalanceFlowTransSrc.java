package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/22 17:07
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/22 xujincai init
 */
public enum ShopBalanceFlowTransSrc {
    ORDER_ADD(1, "订单收入"),
    WITHDRAWAL_SUB(2, "提现支出"),
    WITHDRAWAL_ADD(3, "提现收入（审核失败）");

    int code;
    String message;

    ShopBalanceFlowTransSrc(int code, String message) {
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
            for (ShopBalanceFlowTransSrc ms : ShopBalanceFlowTransSrc.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
