package cn.ibdsr.web.modular.platform.cash.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.cash.WithdrawalStatus;
import cn.ibdsr.web.modular.platform.cash.service.IWithdrawalService;
import cn.ibdsr.web.modular.platform.cash.transfer.WithdrawalQueryDTO;
import cn.ibdsr.web.modular.platform.cash.warpper.WithdrawalListWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 提现管理控制器
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 *
 */
@Controller
@RequestMapping("/platform/cash/withdrawal")
public class WithdrawalController extends BaseController {

    @Autowired
    private IWithdrawalService withdrawalService;

    private String PREFIX = "/platform/cash/withdrawal/";

    /**
     * 跳转到提现列表页面
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "withdrawalList.html";
    }

    /**
     * 分页查询提现列表
     *
     * @param queryDTO 搜索条件
     *                 condition 搜索关键字：店铺名称
     *                 timeType 时间类型（1-今日；2-昨日；3-近7日；4-自定义；）
     *                 startDate 统计开始日期
     *                 endDate 统计结束日期
     *                 wdStatus 提现状态（1-待审核；2-审核通过待打款；3-提现成功；4-审核不通过；）
     * @return
     */
    @RequestMapping(value = "/listWithdrawals4Page")
    @ResponseBody
    public Object listWithdrawals4Page(WithdrawalQueryDTO queryDTO) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> resultList = withdrawalService.listWithdrawals4Page(page, queryDTO);
        page.setRecords((List<Map<String, Object>>) new WithdrawalListWarpper(resultList).warp());
        return super.packForBT(page);
    }

    /**
     * 跳转到提现详情页面
     *
     * @param id 提现ID
     * @param model
     * @return
     */
    @RequestMapping("/toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model) {
        model.addAttribute("withdrawal", withdrawalService.detailById(id));
        return PREFIX + "withdrawalDetail.html";
    }

    /**
     * 审核通过
     *
     * @param withdrawalId 提现ID
     * @return
     */
    @RequestMapping(value = "/pass")
    @ResponseBody
    public Object pass(Long withdrawalId) {
        withdrawalService.review(withdrawalId, null, WithdrawalStatus.PASS.getCode());
        return super.SUCCESS_TIP;
    }

    /**
     * 审核不通过
     *
     * @param withdrawalId 提现ID
     * @param reviewRemark 审核说明
     * @return
     */
    @RequestMapping(value = "/refusePass")
    @ResponseBody
    public Object refusePass(Long withdrawalId, String reviewRemark) {
        withdrawalService.review(withdrawalId, reviewRemark, WithdrawalStatus.REFUSE.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 确认打款
     *
     * @param withdrawalId 退款订单ID
     * @return
     */
    @RequestMapping(value = "/confirm")
    @ResponseBody
    public Object confirm(Long withdrawalId) {
        withdrawalService.confirm(withdrawalId);
        return SUCCESS_TIP;
    }
}
