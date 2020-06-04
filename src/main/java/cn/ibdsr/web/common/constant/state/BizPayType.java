package cn.ibdsr.web.common.constant.state;

/**
 * @Description 第三方支付类型
 * @Version V1.0
 * @CreateDate 2019-04-23 09:25:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 09:25:23    XuZhipeng               类说明
 *
 */
public enum BizPayType {

    WXPAY(1, "微信"),
    ALIPAY(2, "支付宝"),
    UNIPAY(3, "银联");

    int code;
    String message;

    BizPayType(int code, String message) {
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
            for (BizPayType ms : BizPayType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
