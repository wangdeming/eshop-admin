package cn.ibdsr.web.common.constant.state.hotel;

/**
 * @Description 支付宝接口返回码
 * @Author xjc
 * @Date created in 2018/6/19 13:14
 * @Modifed by
 */
public enum PaymentReturnCode {

    SUCCESS("10000", "调用成功"),
    UNKNOW("20000", "服务不可用"),
    PERMISSIONDENIED("20001", "授权权限不足"),
    MISSPARAMS("40001", "缺少必选参数"),
    ILLEGALPARAMS("40002", "非法的参数"),
    BUSINESSFAILURE("40004", "业务处理失败"),
    NOAUTH("40006", "权限不足"),
    FAIL("99999", "调用失败"),
    ERROR("00000", "调用错误"),
    ;

    String code;
    String message;

    PaymentReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
