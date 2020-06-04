package cn.ibdsr.web.modular.platform.cash.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 收益分成变更记录管理Service
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
public interface IProfitDistributionHistoryService {

    /**
     * 添加服务费变更历史记录
     *
     * @param profitDistributionId 收益分成ID
     * @param beforeServiceRate 变更前服务费率
     * @param afterServiceRate 变更后服务费率
     * @param effectiveTime 生效时间
     */
    void addChangeHistory(Long profitDistributionId, BigDecimal beforeServiceRate, BigDecimal afterServiceRate, Date effectiveTime);

    /**
     * 根据收益分成ID查询店铺名称和类型
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    Map<String, Object> getShopNameAndTypeByProfitDisId(Long profitDistributionId);

    /**
     * 获取收益分成变更历史记录列表
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    List<Map<String, Object>> list(Long profitDistributionId);


}
