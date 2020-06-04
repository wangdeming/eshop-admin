package cn.ibdsr.web.modular.platform.cash.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.cash.service.IProfitDistributionService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 收益分成管理控制器
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
@Controller
@RequestMapping("/platform/cash/profitDistribution")
public class ProfitDistributionController extends BaseController {

    @Autowired
    private IProfitDistributionService profitDistributionService;

    private String PREFIX = "/platform/cash/profitdistribution/";

    /**
     * 跳转到收益分成管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "profitDistributionList.html";
    }

    /**
     * 分页获取收益分成管理列表
     *
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition,
                       @RequestParam(required = false) Integer shopType) {
        Page<Map<String, Object>> page = new PageFactory<Map<String, Object>>().defaultPage();
        List<Map<String, Object>> result = profitDistributionService.list4Page(page, condition, shopType);
        page.setRecords(result);
        return super.packForBT(page);
    }

    /**
     * 跳转到修改收益分成管理页面
     *
     * @param id 分成收益ID
     * @param model
     * @return
     */
    @RequestMapping("/toSet/{id}")
    public String toSet(@PathVariable Long id, Model model) {
        model.addAttribute("profitDis", profitDistributionService.detailById(id));
        return PREFIX + "profitDistributionSet.html";
    }

    /**
     * 修改收益分成管理
     *
     * @param profitDistributionId 收益分成ID
     * @param changeServiceRate 变更后服务费率
     * @param effectiveTime 生效时间
     * @return
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Long profitDistributionId, BigDecimal changeServiceRate, String effectiveTime) {
        profitDistributionService.update(profitDistributionId, changeServiceRate, effectiveTime);
        return super.SUCCESS_TIP;
    }
}
