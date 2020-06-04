package cn.ibdsr.web.modular.platform.shop.account.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.annotion.BussinessLog;
import cn.ibdsr.web.common.constant.dictmap.shopdict.ShopDict;
import cn.ibdsr.web.common.constant.state.shop.ShopAcctOperate;
import cn.ibdsr.web.modular.platform.shop.account.service.IShopAcctOperateService;
import cn.ibdsr.web.modular.platform.shop.account.transfer.AccountOperDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 店铺账户操作管理控制器
 *
 * @author XuZhipeng
 * @Date 2019-02-26 11:26:17
 */
@Controller
@RequestMapping("/platform/shopacctoper")
public class ShopAcctOperateController extends BaseController {

    @Autowired
    private IShopAcctOperateService shopAcctOperateService;

    private String PREFIX = "/platform/shop/account/";

    /**
     * 跳转到店铺账号冻结页面
     *
     * @param accountId 店铺ID
     * @return
     */
    @RequestMapping("/toFreeze/{accountId}")
    public String toFreeze(@PathVariable Long accountId, Model model) {
        model.addAttribute("acct", shopAcctOperateService.getShopNameAndAccount(accountId));
        return PREFIX + "shopAccount_freeze.html";
    }

    /**
     * 跳转到店铺账号解冻页面
     */
    @RequestMapping("/toUnfreeze/{accountId}")
    public String toUnfreeze(@PathVariable Long accountId, Model model) {
        model.addAttribute("acct", shopAcctOperateService.getShopNameAndAccount(accountId));
        return PREFIX + "shopAccount_unfreeze.html";
    }

    /**
     * 跳转到店铺账号操作记录页面
     */
    @RequestMapping("/toOperRecord/{accountId}")
    public String toOperRecord(@PathVariable Long accountId, Model model) {
        model.addAttribute("acct", shopAcctOperateService.getShopNameAndAccount(accountId));
        return PREFIX + "shopAccount_operRecord.html";
    }

    /**
     * 冻结店铺账号
     */
    @RequestMapping(value = "/freeze")
    @BussinessLog(name = "冻结店铺账号", key = "accountId", dict = ShopDict.ShopUserDict)
    @ResponseBody
    public Object freeze (AccountOperDTO accountOperDTO) {
        shopAcctOperateService.addAcctOperRecord(accountOperDTO, ShopAcctOperate.FREEZE.getCode());
        return super.SUCCESS_TIP;
    }

    /**
     * 解冻店铺账号
     */
    @RequestMapping(value = "/unfreeze")
    @BussinessLog(name = "解冻店铺账号", key = "accountId", dict = ShopDict.ShopUserDict)
    @ResponseBody
    public Object unfreeze(AccountOperDTO accountOperDTO) {
        shopAcctOperateService.addAcctOperRecord(accountOperDTO, ShopAcctOperate.UNFREEZE.getCode());
        return super.SUCCESS_TIP;
    }

    /**
     * 获取店铺账号操作记录列表
     *
     * @param accountId 店铺账户ID
     * @return
     */
    @RequestMapping(value = "/listOperRecords")
    @ResponseBody
    public Object listOperRecords(Long accountId) {
        List<Map<String, Object>> resultList = shopAcctOperateService.listOperRecords(accountId);
        return new SuccessDataTip(resultList);
    }

    /**
     * 注销店铺账号
     */
    @RequestMapping(value = "/cancel")
    @BussinessLog(name = "注销店铺账号", key = "accountId", dict = ShopDict.ShopUserDict)
    @ResponseBody
    public Object cancel(Long accountId) {
        shopAcctOperateService.cancel(accountId);
        return super.SUCCESS_TIP;
    }
}
