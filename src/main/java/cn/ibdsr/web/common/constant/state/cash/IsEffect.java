package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description 是否立即生效
 * @Version V1.0
 * @CreateDate 2019-04-22 09:25:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 09:25:22    XuZhipeng               类说明
 *
 */
public enum IsEffect {
    NO(0, "否"), YES(1, "是");

    int code;
    String message;

    IsEffect(int code, String message) {
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
