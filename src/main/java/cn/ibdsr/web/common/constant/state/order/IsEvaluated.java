package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 订单是否评价
 * @Version V1.0
 * @CreateDate 2019-03-20 08:31:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-03-20 08:31:22    XuZhipeng               类说明
 *
 */
public enum IsEvaluated {
    NO(0, "待评价"), YES(1, "已评价");

    int code;
    String message;

    IsEvaluated(int code, String message) {
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
            for (IsEvaluated ms : IsEvaluated.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}
