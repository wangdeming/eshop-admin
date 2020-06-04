package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/22 15:14
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/22 xujincai init
 */
public enum CashWithdrawalStatus {

    WAIT_AUDIT(1, "待审核"),
    AUDIT_YES(2, "审核通过"),
    CONFIRM_PAYMENT(3, "提现成功"),
    AUDIT_NO(4, "审核不通过");

    int code;
    String message;

    CashWithdrawalStatus(int code, String message) {
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
