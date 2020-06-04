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
public enum OrderCashType {

    NULL(-1, ""),
    OUTACC_AMOUNT(1, "待出账金额"),
    INCOME_AMOUNT(2, "待到账金额"),
    ;

    int code;
    String message;

    OrderCashType(int code, String message) {
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


