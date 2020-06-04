package cn.ibdsr.web.common.constant.state.cash;

/**
 * @Description 查询时间类型
 * @Version V1.0
 * @CreateDate 2019-04-22 09:25:22
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 09:25:22    XuZhipeng               类说明
 *
 */
public enum QueryTimeType {
    TODAY(1, "今日"), YESTERDAY(2, "昨日"), LAST7DAYS(3, "近7日"), CUSTOMIZE(4, "自定义");

    int code;
    String message;

    QueryTimeType(int code, String message) {
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
