package cn.ibdsr.web.modular.platform.cash.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description 收益分成变更记录管理控制器
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
@Controller
@RequestMapping("/platform/cash/profitDistributionHistory")
public class ProfitDistributionHistoryController extends BaseController {

    @Autowired
    private IProfitDistributionHistoryService profitDistributionHistoryService;

    private String PREFIX = "/platform/cash/profitdistribution/";

    /**
     * 跳转到收益分成变更记录页面
     */
    @RequestMapping("/toHistoryList/{profitDistributionId}")
    public String toList(@PathVariable Long profitDistributionId, Model model) {
        model.addAttribute("profitDistributionId", profitDistributionId);
        model.addAttribute("shop", profitDistributionHistoryService.getShopNameAndTypeByProfitDisId(profitDistributionId));
        return PREFIX + "profitDistributionHistoryList.html";
    }

    /**
     * 获取收益分成变更历史记录列表
     *
     * @param profitDistributionId 收益分成ID
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Long profitDistributionId) {
        List<Map<String, Object>> result = profitDistributionHistoryService.list(profitDistributionId);
        return new SuccessDataTip(result);
    }

}
