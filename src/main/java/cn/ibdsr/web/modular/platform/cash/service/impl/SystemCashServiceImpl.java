package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.cash.PlatformBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.cash.QueryTimeType;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.PlatformBalanceFlowMapper;
import cn.ibdsr.web.common.persistence.model.PlatformBalanceFlow;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.modular.platform.cash.dao.SystemCashDao;
import cn.ibdsr.web.modular.platform.cash.service.ISystemCashService;
import cn.ibdsr.web.modular.platform.cash.transfer.PlatformAccountVO;
import cn.ibdsr.web.modular.platform.cash.transfer.PlatformBalanceFlowVO;
import cn.ibdsr.web.modular.platform.cash.transfer.QueryDTO;
import cn.ibdsr.web.modular.platform.cash.transfer.TodayDataVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 系统资金详情Service
 * @Version V1.0
 * @CreateDate 2019-04-22 14:12:11
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:12:11    XuZhipeng               类说明
 */
@Service
public class SystemCashServiceImpl implements ISystemCashService {

    @Autowired
    private SystemCashDao systemCashDao;

    @Autowired
    private PlatformBalanceFlowMapper platformBalanceFlowMapper;

    /**
     * 查询平台账户概览
     *
     * @return
     */
    @Override
    public PlatformAccountVO getPlatformAccountInfo() {
        PlatformAccountVO platformAccountVO = new PlatformAccountVO();

        // 1.查询平台当前余额
        platformAccountVO.setPlatformBalance(AmountFormatUtil.amountFormat(getPlatformBalance()));

        // 2.查询店铺总余额
        platformAccountVO.setShopBalance(AmountFormatUtil.amountFormat(systemCashDao.getShopBalance()));

        // 3.查询服务费总额
        platformAccountVO.setServiceAmount(AmountFormatUtil.amountFormat(systemCashDao.getServiceAmount()));

        // 4.查询历史累计余额
        platformAccountVO.setTotalBalance(AmountFormatUtil.amountFormat(systemCashDao.getPlatformTotalBalance()));

        // 5.历史累计提现
        platformAccountVO.setWithdrawalAmount(AmountFormatUtil.amountFormat(systemCashDao.getWithdrawalAmount()));
        return platformAccountVO;
    }

    /**
     * 查询今日实时数据
     *
     * @return
     */
    @Override
    public TodayDataVO getTodayData() {
        TodayDataVO todayDataVO = new TodayDataVO();

        // 1.查询今日余额变化
        todayDataVO.setChangeBalance(AmountFormatUtil.amountFormat(systemCashDao.getTodayChangeBalance()));

        // 查询今日订单成交额和成交笔数
        Map<String, Object> orderMap = systemCashDao.getTodayOrderAmountAndNum();

        // 2.订单成交额
        todayDataVO.setOrderAmount(AmountFormatUtil.amountFormat(orderMap.get("orderAmount")));

        // 3.成交笔数
        todayDataVO.setOrderNum(Integer.valueOf(orderMap.get("orderNum").toString()));

        // 查询今日退款金额和退款笔数
        Map<String, Object> refundMap = systemCashDao.getTodayRefundAmountAndNum();
        Map<String, Object> hotelRefundMap = systemCashDao.getTodayHotelRefundAmountAndNum();

        // 4.退款金额
        todayDataVO.setRefundAmount(
                AmountFormatUtil.amountFormat(
                        new BigDecimal(
                                AmountFormatUtil.amountFormat(refundMap.get("refundAmount"))
                        ).add(
                                new BigDecimal(
                                        AmountFormatUtil.amountFormat(hotelRefundMap.get("refundAmount"))
                                )
                        )
                )
        );

        // 5.退款笔数
        todayDataVO.setRefundNum(Integer.valueOf(refundMap.get("refundNum").toString()) + Integer.valueOf(hotelRefundMap.get("refundNum").toString()));

        // 查询今日退款金额和退款笔数
        Map<String, Object> withdrawalMap = systemCashDao.getTodayWithdrawalAmountAndNum();

        // 6.提现金额
        todayDataVO.setWithdrawalAmount(AmountFormatUtil.amountFormat(withdrawalMap.get("withdrawalAmount")));

        // 7.提现笔数
        todayDataVO.setWithdrawalNum(Integer.valueOf(withdrawalMap.get("withdrawalNum").toString()));
        return todayDataVO;
    }

    /**
     * 查询今日平台余额收支明细列表
     *
     * @return
     */
    @Override
    public List<PlatformBalanceFlowVO> listTodayPlatformBalanceFlow() {
        List<PlatformBalanceFlowVO> flowVOList = systemCashDao.listTodayPlatformBalanceFlow();
        // 数据包装
        balanceFlowWarpper(flowVOList);
        return flowVOList;
    }

    /**
     * 分页获取资金交易记录列表
     *
     * @param page
     * @param queryDTO 搜索条件
     *                 condition 搜索关键字：店铺名称
     *                 timeType 时间类型（1-今日；2-昨日；3-近7日；4-自定义；）
     *                 startDate 统计开始日期
     *                 endDate 统计结束日期
     * @return
     */
    @Override
    public List<PlatformBalanceFlowVO> listPlatformBalanceFlow4Page(Page page, QueryDTO queryDTO) {
        StaticCheck.check(queryDTO);

        // 查询时间类型为自定义，判断查询开始时间和结束时间不能为空
        if (QueryTimeType.CUSTOMIZE.getCode() == queryDTO.getTimeType()) {
            if (ToolUtil.isEmpty(queryDTO.getStartDate())) {
                throw new BussinessException(BizExceptionEnum.QUERY_START_DATE_IS_NULL);
            }
            if (ToolUtil.isEmpty(queryDTO.getEndDate())) {
                throw new BussinessException(BizExceptionEnum.QUERY_END_DATE_IS_NULL);
            }
        }

        List<PlatformBalanceFlowVO> flowVOList = systemCashDao.listPlatformBalanceFlow4Page(page, queryDTO, page.getOrderByField(), page.isAsc());
        // 数据包装
        balanceFlowWarpper(flowVOList);
        return flowVOList;
    }

    /**
     * 查询平台当前余额
     *
     * @return
     */
    private BigDecimal getPlatformBalance() {
        List<PlatformBalanceFlow> flowList = platformBalanceFlowMapper.selectList(
                new EntityWrapper<PlatformBalanceFlow>()
                        .orderBy("created_time", Boolean.FALSE)
                        .last("LIMIT 1"));
        if (null != flowList && 0 < flowList.size()) {
            return flowList.get(0).getBalance();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 平台余额流水数据包装
     *
     * @param flowVOList 余额流水集合
     */
    private void balanceFlowWarpper(List<PlatformBalanceFlowVO> flowVOList) {
        for (PlatformBalanceFlowVO flowVO : flowVOList) {
            // 数据转换
            // 金额变动格式
            flowVO.setAmount(PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode() == flowVO.getTransSrc()
                    ? "-" + AmountFormatUtil.amountFormat(flowVO.getAmount()) : "+" + AmountFormatUtil.amountFormat(flowVO.getAmount()));

            // 店铺类型
            flowVO.setShopType(ShopType.valueOf(Integer.valueOf(flowVO.getShopType())));

            // 流水类型
            flowVO.setFlowType(PlatformBalanceFlowTransSrc.valueOf(flowVO.getTransSrc()));
        }
    }

}
