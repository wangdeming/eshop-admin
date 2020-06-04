package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.persistence.dao.ProfitDistributionHistoryMapper;
import cn.ibdsr.web.common.persistence.model.ProfitDistribution;
import cn.ibdsr.web.common.persistence.model.ProfitDistributionHistory;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionHistoryService;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class ProfitDistributionHistoryServiceImpl implements IProfitDistributionHistoryService {

    @Autowired
    private IProfitDistributionService profitDistributionService;

    @Autowired
    private ProfitDistributionHistoryMapper profitDistributionHistoryMapper;

    /**
     * 添加服务费变更历史记录
     *
     * @param profitDistributionId 收益分成ID
     * @param beforeServiceRate 变更前服务费率
     * @param afterServiceRate 变更后服务费率
     * @param effectiveTime 生效时间
     */
    @Override
    public void addChangeHistory(Long profitDistributionId, BigDecimal beforeServiceRate, BigDecimal afterServiceRate, Date effectiveTime) {
        ProfitDistributionHistory profitDistributionHistory = new ProfitDistributionHistory();
        profitDistributionHistory.setDistributionId(profitDistributionId);
        profitDistributionHistory.setBeforeServiceRate(beforeServiceRate);          // 变更前费率
        profitDistributionHistory.setAfterServiceRate(afterServiceRate);            // 变更后费率
        profitDistributionHistory.setEffectiveTime(effectiveTime);                  // 生效时间
        profitDistributionHistory.setCreatedTime(new Date());

        // 管理员信息
        ShiroUser adminUser = ShiroKit.getUser();
        profitDistributionHistory.setCreatedUser(adminUser.getId());                // 管理员ID
        profitDistributionHistory.setCreatedUserName(adminUser.getName());          // 管理员姓名
        profitDistributionHistory.setCreatedUserAccount(adminUser.getAccount());    // 管理员账号
        profitDistributionHistory.setIsDeleted(IsDeleted.NORMAL.getCode());
        profitDistributionHistory.insert();
    }

    /**
     * 根据收益分成ID查询店铺名称和类型
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    @Override
    public Map<String, Object> getShopNameAndTypeByProfitDisId(Long profitDistributionId) {
        // 校验收益分成ID
        ProfitDistribution profitDistribution = profitDistributionService.checkProfitDisId(profitDistributionId);

        // 查询店铺名称和类型
        Map<String, Object> shopMap = profitDistributionService.getShopNameAndTypeByShopId(profitDistribution.getShopId());
        return shopMap;
    }

    /**
     * 获取收益分成变更历史记录列表
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Long profitDistributionId) {
        // 查询店铺服务费率变更记录
        StringBuilder sqlSelectSb = new StringBuilder();
        sqlSelectSb.append("before_service_rate AS beforeServiceRate,")
                .append("after_service_rate AS afterServiceRate,")
                .append("DATE_FORMAT(effective_time, '%Y-%m-%d') AS effectiveTime,")
                .append("created_user_name AS createdUserName,")
                .append("created_user_account AS createdUserAccount,")
                .append("created_time AS createdTime");
        List<Map<String, Object>> historyList = profitDistributionHistoryMapper.selectMaps(
                new EntityWrapper<ProfitDistributionHistory>()
                        .setSqlSelect(sqlSelectSb.toString())
                        .eq("distribution_id", profitDistributionId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .orderBy("created_time", false));
        for (Map<String, Object> map : historyList) {
            map.put("beforeServiceRate", AmountFormatUtil.convertPercent(map.get("beforeServiceRate")));    // 变更前费率小数转百分比
            map.put("afterServiceRate", AmountFormatUtil.convertPercent(map.get("afterServiceRate")));      // 变更后费率小数转百分比
        }
        return historyList;
    }

}
