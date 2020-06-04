package cn.ibdsr.web.common.constant.state;

/**
 * @Description description
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/18 16:34
 * @Modifed by
 */
public enum MessageStatus {
    Ok(200, "短信发送成功！"), ERRO(400, "短信发送失败！");
    Integer code;
    String message;

    MessageStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
            for (MessageStatus ms : MessageStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }


}
