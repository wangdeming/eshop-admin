package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description 提现状态
 * @Version V1.0
 * @CreateDate 2019-04-23 09:25:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 09:25:23    XuZhipeng               类说明
 *
 */
public enum WithdrawalStatus {

    WAIT_REVIEW(1, "待审核"),
    PASS(2, "审核通过"),
    CONFIRM(3, "确认打款"),
    REFUSE(4, "审核不通过");

    int code;
    String message;

    WithdrawalStatus(int code, String message) {
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
            for (WithdrawalStatus ms : WithdrawalStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
