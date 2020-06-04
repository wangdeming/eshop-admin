package cn.ibdsr.web.common.constant.state.platform;

/**
 * @Description 广告状态
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */
public enum AdStatus {

    PUBLISH(1, "发布"), NORMAL(2, "正常"), OFFSHELF(3, "下架");

    int code;
    String message;

    AdStatus(int code, String message) {
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
            for (AdStatus ms : AdStatus.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
