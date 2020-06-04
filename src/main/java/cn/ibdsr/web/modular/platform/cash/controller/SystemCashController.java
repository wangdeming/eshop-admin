package cn.ibdsr.web.modular.platform.cash.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.cash.service.ISystemCashService;
import cn.ibdsr.web.modular.platform.cash.transfer.PlatformAccountVO;
import cn.ibdsr.web.modular.platform.cash.transfer.QueryDTO;
import cn.ibdsr.web.modular.platform.cash.transfer.PlatformBalanceFlowVO;
import cn.ibdsr.web.modular.platform.cash.transfer.TodayDataVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 系统资金详情控制器
 * @Version V1.0
 * @CreateDate 2019-04-22 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:12:11    XuZhipeng               类说明
 *
 */
@Controller
@RequestMapping("/platform/cash/systemCash")
public class SystemCashController extends BaseController {

    @Autowired
    private ISystemCashService systemCashService;

    private String PREFIX = "/platform/cash/systemcash/";

    /**
     * 跳转到系统资金详情首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "systemCashInfo.html";
    }

    /**
     * 查询平台账户概览
     *
     * @return
     */
    @RequestMapping(value = "/getPlatformAccountInfo")
    @ResponseBody
    public Object getPlatformAccountInfo() {
        PlatformAccountVO platformAccountVO = systemCashService.getPlatformAccountInfo();
        return new SuccessDataTip(platformAccountVO);
    }

    /**
     * 查询今日实时数据
     *
     * @return
     */
    @RequestMapping(value = "/getTodayData")
    @ResponseBody
    public Object getTodayData() {
        TodayDataVO todayDataVO = systemCashService.getTodayData();
        return new SuccessDataTip(todayDataVO);
    }

    /**
     * 查询今日平台余额收支明细列表
     *
     * @return
     */
    @RequestMapping(value = "/listTodayPlatformBalanceFlow")
    @ResponseBody
    public Object listTodayPlatformBalanceFlow() {
        List<PlatformBalanceFlowVO> flowList = systemCashService.listTodayPlatformBalanceFlow();
        return new SuccessDataTip(flowList);
    }

    /**
     * 跳转到平台余额流水页面
     */
    @RequestMapping(value = "/toFlowList")
    public String toFlowList() {
        return PREFIX + "platformBalanceFlowList.html";
    }

    /**
     * 分页查询平台余额收支明细列表
     *
     * @param queryDTO 搜索条件
     *                 condition 搜索关键字：店铺名称
     *                 timeType 时间类型（1-今日；2-昨日；3-近7日；4-自定义；）
     *                 startDate 统计开始日期
     *                 endDate 统计结束日期
     * @return
     */
    @RequestMapping(value = "/listPlatformBalanceFlow4Page")
    @ResponseBody
    public Object listPlatformBalanceFlow4Page(QueryDTO queryDTO) {
        Page<PlatformBalanceFlowVO> page = new PageFactory<PlatformBalanceFlowVO>().defaultPage();
        List<PlatformBalanceFlowVO> flowList = systemCashService.listPlatformBalanceFlow4Page(page, queryDTO);
        page.setRecords(flowList);
        return super.packForBT(page);
    }
}
