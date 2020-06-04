package cn.ibdsr.web.common.constant.state.order;

/**
 * @Description 订单商品退款审核操作类型枚举类
 * @Version V1.0
 * @CreateDate 2019-04-02 13:44:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-02 13:44:22    XuZhipeng               类说明
 *
 */
public enum ReviewOperType {

    NULL(-1, ""),
    PASS(1, "审核通过"),
    REFUSE_PASS(2, "审核不通过"),
    ;

    int code;
    String message;

    ReviewOperType(int code, String message) {
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
            for (ReviewOperType ms : ReviewOperType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }

}


