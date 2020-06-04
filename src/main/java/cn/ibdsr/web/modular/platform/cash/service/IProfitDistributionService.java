package cn.ibdsr.web.modular.platform.cash.service;

import cn.ibdsr.web.common.persistence.model.ProfitDistribution;
import com.baomidou.mybatisplus.plugins.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 收益分成管理Service
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
public interface IProfitDistributionService {

    /**
     * 初始化店铺服务费率
     *
     * @param shopId 店铺ID
     */
    void initShopSerRate(Long shopId);

    /**
     * 根据店铺ID删除店铺服务费率
     *
     * @param shopId 店铺ID
     */
    void deleteShopSerRate(Long shopId);

    /**
     * 分页获取收益分成管理列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<Map<String,Object>> list4Page(Page page, String condition, Integer shopType);

    /**
     * 查询店铺服务费设置详情
     *
     * @param id 收益分成ID
     * @return
     */
    Map<String,Object> detailById(Long id);

    /**
     * 修改收益分成管理
     *
     * @param profitDistributionId 收益分成ID
     * @param changeServiceRate 变更后服务费率
     * @param effectiveTime 生效时间
     */
    void update(Long profitDistributionId, BigDecimal changeServiceRate, String effectiveTime);

    /**
     * 校验收益分成ID，并返回配置信息
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    ProfitDistribution checkProfitDisId(Long profitDistributionId);

    /**
     * 根据店铺ID查询店铺名称和类型
     *
     * @param shopId 店铺ID
     * @return
     */
    Map<String, Object> getShopNameAndTypeByShopId(Long shopId);

    /**
     * 根据店铺ID查询店铺服务费率
     *
     * @param shopId 店铺ID
     * @return
     */
    BigDecimal getServiceRateByShopId(Long shopId);
}
