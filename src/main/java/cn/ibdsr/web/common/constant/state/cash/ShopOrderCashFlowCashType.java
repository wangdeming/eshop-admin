package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/22 15:54
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/22 xujincai init
 */
public enum ShopOrderCashFlowCashType {
    DEBIT_AMOUNT(1, "待出账金额"),
    CREDIT_AMOUNT(2, "待到账金额");

    int code;
    String message;

    ShopOrderCashFlowCashType(int code, String message) {
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
