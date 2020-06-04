package cn.ibdsr.web.modular.platform.cash.service;

import cn.ibdsr.web.modular.platform.cash.transfer.PlatformAccountVO;
import cn.ibdsr.web.modular.platform.cash.transfer.QueryDTO;
import cn.ibdsr.web.modular.platform.cash.transfer.PlatformBalanceFlowVO;
import cn.ibdsr.web.modular.platform.cash.transfer.TodayDataVO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Description 系统资金详情Service
 * @Version V1.0
 * @CreateDate 2019-04-22 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:12:11    XuZhipeng               类说明
 *
 */
public interface ISystemCashService {

    /**
     * 查询平台账户概览
     *
     * @return
     */
    PlatformAccountVO getPlatformAccountInfo();

    /**
     * 查询今日实时数据
     *
     * @return
     */
    TodayDataVO getTodayData();

    /**
     * 查询今日平台余额收支明细列表
     *
     * @return
     */
    List<PlatformBalanceFlowVO> listTodayPlatformBalanceFlow();

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
    List<PlatformBalanceFlowVO> listPlatformBalanceFlow4Page(Page page, QueryDTO queryDTO);
}
