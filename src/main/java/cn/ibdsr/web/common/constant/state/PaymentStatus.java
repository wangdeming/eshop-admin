package cn.ibdsr.web.common.constant.state;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/3/27 11:07
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/3/27 xujincai init
 */
public enum PaymentStatus {

    SUBMITTED(1, "已提交"),
    SUCCESS(2, "支付成功"),
    FAIL(3, "支付失败"),
    ;

    int code;
    String message;

    PaymentStatus(int code, String message) {
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
