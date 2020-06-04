package cn.ibdsr.web.common.constant.state.platform;

/**
 * @Description 广告关联类型（1-商品；2-店铺；3-URL；4-无关联；
 * @Version V1.0
 * @CreateDate 2019/4/3 14:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/3      ZhuJingrui            类说明
 */


public enum AdType {

    GOODS(1, "商品"), SHOP(2, "店铺"), URL(3, "URL"), NORELATION(4, "无关联");

    int code;
    String message;

    AdType(int code, String message) {
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
            for (AdType ms : AdType.values()) {
                if (ms.getCode() == value) {
                    return ms.getMessage();
                }
            }
            return "";
        }
    }
}
