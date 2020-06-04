package cn.ibdsr.web.common.constant.state;

/**
 * @Description 消息队列处理状态
 * @Version V1.0
 * @CreateDate 2019-04-26 10:26:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-26 10:26:22    XuZhipeng               类说明
 *
 */
public enum MessageQueueHandleStatus {

    MSG_LOSED(-2, "消息丢失"),          // 消费未成功发送到交换机
    EX_NOBIND_QUEUE(-1, "消息丢失"),    // 交换机未绑定队列
    WAIT_HANDLE(1, "待处理"),
    SUCCESS(2, "处理成功"),
    FAIL(3, "处理失败"),
    ;

    int code;
    String message;

    MessageQueueHandleStatus(int code, String message) {
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
            for (MessageQueueHandleStatus ms : MessageQueueHandleStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }


}
