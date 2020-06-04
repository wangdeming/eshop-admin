package cn.ibdsr.web.modular.platform.shop.account.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.modular.platform.shop.account.service.IShopAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 店铺账户管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
@Controller
@RequestMapping("/platform/shopaccount")
public class ShopAccountController extends BaseController {

    @Autowired
    private IShopAccountService shopAccountService;

    private String PREFIX = "/platform/shop/account/";

    /**
     * 跳转到店铺信息管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shopAccountList.html";
    }

    /**
     * 跳转到新增店铺主账号页面
     *
     * @param shopId 店铺ID
     * @return
     */
    @RequestMapping("/toAdd/{shopId}")
    public String toEdit(@PathVariable Long shopId, Model model) {
        model.addAttribute("shopId", shopId);
        return PREFIX + "shopAccount_add.html";
    }

    /**
     * 跳转到查看店铺账号详情页面
     *
     * @param accountId 店铺账号ID
     * @return
     */
    @RequestMapping("/toView/{accountId}")
    public String toView(@PathVariable Long accountId, Model model) {
        model.addAttribute("acct", shopAccountService.getAccountInfo(accountId));
        return PREFIX + "shopAccount_view.html";
    }

    /**
     * 开通店铺账户
     *
     * @param shopId 店铺ID
     * @param account 账户名称
     * @return
     */
    @RequestMapping(value = "/openAccount")
    @ResponseBody
    public Object openAccount(Long shopId, String account) {
        shopAccountService.openAccount(shopId, account);
        return super.SUCCESS_TIP;
    }

    /**
     * 查询未开通账户的店铺列表
     *
     * @return
     */
    @RequestMapping(value = "/listNoAccountShops")
    @ResponseBody
    public Object listNoAccountShops() {
        List<Map<String, Object>> resultList = shopAccountService.listNoAccountShops();
        return new SuccessDataTip(resultList);
    }

    /**
     * 根据店铺ID查询新增主账号页面需要的信息
     *
     * @param shopId 店铺ID
     * @return
     */
    @RequestMapping(value = "/getShopInfoById")
    @ResponseBody
    public Object getShopInfoById(Long shopId) {
        Map<String, Object> result = shopAccountService.getShopInfoById(shopId);
        return new SuccessDataTip(result);
    }

    /**
     * 获取店铺账号列表
     *
     * @param condition 搜索关键字（店铺名/账户名/手机号）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition,
                       @RequestParam(required = false) Integer shopType) {
        return shopAccountService.list(condition, shopType);
    }
}
