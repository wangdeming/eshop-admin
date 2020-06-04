package cn.ibdsr.web.modular.platform.cash.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

/**
 * @Description: 列表查询DTO
 * @Version: V1.0
 * @CreateDate: 2019-04-22 16:53:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 16:53:11     XuZhipeng               类说明
 *
 */
public class QueryDTO extends BaseDTO {

    /**
     * 搜索关键字：店铺名称
     */
    private String condition;

    /**
     * 时间类型（1-今日；2-昨日；3-近7日；4-自定义；）
     */
    @Verfication(name = "时间类型（1-今日；2-昨日；3-近7日；4-自定义；）", notNull = true)
    private Integer timeType;

    /**
     * 自定义开始时间
     */
    private String startDate;

    /**
     * 自定义结束时间
     */
    private String endDate;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
