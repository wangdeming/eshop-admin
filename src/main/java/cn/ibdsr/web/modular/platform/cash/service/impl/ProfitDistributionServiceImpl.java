package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.DateUtil;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.cash.IsEffect;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ProfitDistributionMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.model.ProfitDistribution;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.DateUtils;
import cn.ibdsr.web.modular.platform.cash.dao.ProfitDistributionDao;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionHistoryService;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
@Service
public class ProfitDistributionServiceImpl implements IProfitDistributionService {

    @Autowired
    private ProfitDistributionDao profitDistributionDao;

    @Autowired
    private IProfitDistributionHistoryService profitDistributionHistoryService;

    @Autowired
    private ProfitDistributionMapper profitDistributionMapper;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 初始化店铺服务费率
     *
     * @param shopId 店铺ID
     */
    @Override
    public void initShopSerRate(Long shopId) {
        ProfitDistribution profitDistribution = new ProfitDistribution();
        profitDistribution.setShopId(shopId);
        profitDistribution.setServiceRate(BigDecimal.ZERO);
        profitDistribution.setIsEffect(IsEffect.YES.getCode());
        profitDistribution.setCreatedTime(new Date());
        profitDistribution.setCreatedUser(ShiroKit.getUser().getId());
        profitDistribution.setIsDeleted(IsDeleted.NORMAL.getCode());
        profitDistribution.insert();
    }

    /**
     * 根据店铺ID删除店铺服务费率
     *
     * @param shopId 店铺ID
     */
    @Override
    public void deleteShopSerRate(Long shopId) {
        ProfitDistribution profitDistribution = new ProfitDistribution();
        profitDistribution.setIsDeleted(IsDeleted.DELETED.getCode());
        profitDistribution.setModifiedTime(new Date());
        profitDistribution.setModifiedUser(ShiroKit.getUser().getId());
        profitDistributionMapper.update(profitDistribution, new EntityWrapper<ProfitDistribution>().eq("shop_id", shopId));
    }

    /**
     * 分页获取收益分成管理列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @Override
    public List<Map<String, Object>> list4Page(Page page, String condition, Integer shopType) {

        List<Map<String, Object>> resultList = profitDistributionDao.list4Page(page, condition, shopType, page.getOrderByField(), page.isAsc());
        // 当前时间和生效时间进行比较
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> profitDist = resultList.get(i);

            if (null != profitDist.get("effectiveTime")) {
                // 生效时间
                Date effectiveTime = DateUtil.parse(profitDist.get("effectiveTime").toString(), "yyyy-MM-dd HH:mm:ss.SSS");
                if (new Date().after(effectiveTime)) {
                    profitDist.put("serviceRate", profitDist.get("changeServiceRate"));

                    // 更新当前费率
                    ProfitDistribution distribution = new ProfitDistribution();
                    distribution.setId(Long.valueOf(profitDist.get("id").toString()));
                    distribution.setServiceRate(new BigDecimal(profitDist.get("changeServiceRate").toString()));
                    distribution.setIsEffect(IsEffect.YES.getCode());
                    distribution.setModifiedTime(new Date());
                    distribution.updateById();
                }
            }

            // 店铺类型名称
            profitDist.put("shopType", ShopType.valueOf(Integer.valueOf(profitDist.get("shopType").toString())));

            // 创建时间
            profitDist.put("createdTime", profitDist.get("createdTime").toString().substring(0, 10));

            // 当前费率
            profitDist.put("serviceRate", AmountFormatUtil.convertPercent(profitDist.get("serviceRate")));

            // 变更后费率
            if (ToolUtil.isNotEmpty(profitDist.get("changeServiceRate"))) {
                profitDist.put("changeServiceRate", AmountFormatUtil.convertPercent(profitDist.get("changeServiceRate")));
            }
        }
        return resultList;
    }

    /**
     * 获取店铺服务费率详情
     *
     * @param profitDistributionId 收益分成ID
     */
    @Override
    public Map<String, Object> detailById(Long profitDistributionId) {
        // 校验收益分成ID，并返回配置信息
        ProfitDistribution distribution = checkProfitDisId(profitDistributionId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("id", distribution.getId());                                                          // 收益分成ID
        resultMap.put("serviceRate", AmountFormatUtil.convertPercent(distribution.getServiceRate()));        // 店铺当前服务费率
        resultMap.put("changeServiceRate", AmountFormatUtil.convertPercent(distribution.getServiceRate()));  // 变更后费率
        String effectiveTime = null != distribution.getEffectiveTime()
                ? DateUtil.format(distribution.getEffectiveTime(), "yyyy-MM-dd HH:mm:ss") : null;
        resultMap.put("effectiveTime", effectiveTime); // 生效时间

        // 查询店铺信息
        Shop shop = shopMapper.selectById(distribution.getShopId());
        resultMap.put("shopName", shop.getName());
        resultMap.put("shopType", ShopType.valueOf(shop.getType()));
        return resultMap;

    }

    /**
     * 修改收益分成管理
     *
     * @param profitDistributionId 收益分成ID
     * @param changeServiceRate 变更后服务费率
     * @param effectiveTime 生效时间
     */
    @Override
    public void update(Long profitDistributionId, BigDecimal changeServiceRate, String effectiveTime) {
        if (null == changeServiceRate) {
            throw new BussinessException(BizExceptionEnum.CHANGE_SERVICE_RATE_IS_NULL);
        }
        if (null == effectiveTime) {
            throw new BussinessException(BizExceptionEnum.EFFECTIVE_TIME_IS_NULL);
        }
        // 校验变更服务费率范围
        if (BigDecimal.ZERO.compareTo(changeServiceRate) > 0
                || BigDecimal.valueOf(100).compareTo(changeServiceRate) < 0) {
            throw new BussinessException(BizExceptionEnum.CHANGE_SERVICE_RATE_IS_ERROR);
        }

        // 生效时间字符串转Date
        Date effTime = convertStr2Date(effectiveTime);
        // 校验生效时间是否为：从明天开始一年内
        if (!checkEffectiveTime(effTime)) {
            throw new BussinessException(BizExceptionEnum.EFFECTIVE_TIME_IS_ERROR);
        }

        // 变更后费率 = %/100
        changeServiceRate = changeServiceRate.divide(BigDecimal.valueOf(100));

        // 校验收益分成ID，并返回配置信息
        ProfitDistribution profitDis = checkProfitDisId(profitDistributionId);
        // 校验变更后费率不能与当前费率一致
        if (changeServiceRate.compareTo(profitDis.getServiceRate()) == 0) {
            throw new BussinessException(BizExceptionEnum.CHANGE_SERRATE_CANNOT_EQUALS_SERRATE);
        }

        // 非第一次变更，修改当前费率为上一次变更费率
        if (IsEffect.NO.getCode() == profitDis.getIsEffect()) {
            BigDecimal serviceRate = profitDis.getChangeServiceRate() == null ? BigDecimal.ZERO : profitDis.getChangeServiceRate();
            profitDis.setServiceRate(serviceRate);
        }
        profitDis.setChangeServiceRate(changeServiceRate);
        profitDis.setEffectiveTime(effTime);
        profitDis.setIsEffect(IsEffect.NO.getCode());
        profitDis.setModifiedTime(new Date());
        profitDis.setModifiedUser(ShiroKit.getUser().getId());
        profitDis.updateById();

        // 插入变更历史记录
        profitDistributionHistoryService.addChangeHistory(
                profitDistributionId,
                profitDis.getServiceRate(),
                profitDis.getChangeServiceRate(),
                effTime);
    }

    /**
     * 校验收益分成ID，并返回配置信息
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    @Override
    public ProfitDistribution checkProfitDisId(Long profitDistributionId) {
        if (null == profitDistributionId) {
            throw new BussinessException(BizExceptionEnum.PROFIT_DISTRIBUTION_ID_IS_NULL);
        }
        ProfitDistribution profitDistribution = profitDistributionMapper.selectById(profitDistributionId);
        if (null == profitDistribution) {
            throw new BussinessException(BizExceptionEnum.PROFIT_DISTRIBUTION_IS_NOT_EXIST);
        }
        return profitDistribution;
    }

    /**
     * 根据店铺ID查询店铺名称和类型
     *
     * @param shopId 店铺ID
     * @return
     */
    @Override
    public Map<String, Object> getShopNameAndTypeByShopId(Long shopId) {
        Shop shop = shopMapper.selectById(shopId);
        if (null == shop) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_EXIST);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("shopName", shop.getName());
        resultMap.put("shopType", ShopType.valueOf(shop.getType()));
        return resultMap;
    }

    /**
     * 根据店铺ID查询店铺服务费率
     *
     * @param shopId 店铺ID
     * @return
     */
    @Override
    public BigDecimal getServiceRateByShopId(Long shopId) {
        // 查询店铺服务费率
        List<ProfitDistribution> distributionList = profitDistributionMapper.selectList(
                new EntityWrapper<ProfitDistribution>()
                        .eq("shop_id", shopId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .last("Limit 1"));
        if (null == distributionList || 0 == distributionList.size()) {
            return BigDecimal.ZERO;
        }
        ProfitDistribution profitDis = distributionList.get(0);

        // 判断生效时间是否大于当前时间
        Date effectiveTime = distributionList.get(0).getEffectiveTime();
        if (null != effectiveTime && effectiveTime.before(new Date())) {
            return profitDis.getChangeServiceRate() == null ? profitDis.getServiceRate() : profitDis.getChangeServiceRate();
        }
        return profitDis.getServiceRate();
    }

    /**
     * 生效时间字符串转Date
     *
     * @param dateStr 时间字符串
     * @return
     */
    private Date convertStr2Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date effectTime = null;
        try {
            effectTime = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BussinessException(BizExceptionEnum.EFFECTIVE_TIME_IS_NOT_FORMAT);
        }
        return effectTime;
    }

    /**
     * 校验生效时间是否为：从明天开始一年内
     *
     * @param effectiveTime 生效时间
     * @return
     */
    private Boolean checkEffectiveTime(Date effectiveTime) {
        // 获取明天开始时间
        Date dateOfTomorrow = DateUtils.getDateOfTomorrow();
        // 获取一年后时间
        Date dateOfOneYearLater = DateUtils.getDateOfOneYearLater();

        if (dateOfTomorrow.after(effectiveTime) || dateOfOneYearLater.before(effectiveTime)) {
            return false;
        }
        return true;
    }
}
