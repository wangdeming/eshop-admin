package cn.ibdsr.web.modular.shop.account.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.modular.shop.account.service.SubAccountService;
import cn.ibdsr.web.modular.shop.account.transfer.ShopUserDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商家端子账号操作
 */
@Controller(value = "shopSubAccount")
@RequestMapping(value = "shop/subaccount")
public class SubAccountController {

    @Autowired
    private SubAccountService subAccountService;

    /**
     * 跳转子账号分页列表页面
     *
     * @return
     */
    @RequestMapping(value = "tosubaccountlist")
    public String toSubAccountList() {
        return "/shop/account/subAccountList.html";
    }

    @RequestMapping(value = "subaccountlist")
    @ResponseBody
    public JSONObject subAccountList() {
        return subAccountService.subAccountList();
    }

    /**
     * 冻结和解冻操作
     *
     * @param id
     * @param status 冻结传值3，解冻传值2
     * @return
     */
    @RequestMapping(value = "freezed")
    @ResponseBody
    public SuccessTip freezed(@RequestParam Long id, @RequestParam Integer status) {
        return subAccountService.freezed(id, status);
    }

    /**
     * 子账号注销操作
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    public SuccessTip delete(@RequestParam Long id) {
        return subAccountService.delete(id);
    }

    /**
     * 跳转新增子账号页面
     *
     * @return
     */
    @RequestMapping(value = "toinsertsubaccount")
    public String toInsertSubAccount() {
        return "/shop/account/insertSubAccount.html";
    }

    /**
     * 新增子账号页面数据
     *
     * @return
     */
    @RequestMapping(value = "toinsertsubaccountdata")
    @ResponseBody
    public SuccessDataTip toInsertSubAccountData() {
        return subAccountService.toInsertSubAccountData();
    }

    /**
     * 新增子账号
     *
     * @param shopUserDto
     * @return
     */
    @RequestMapping(value = "insertsubaccount")
    @ResponseBody
    public SuccessTip insertSubAccount(ShopUserDto shopUserDto) {
        return subAccountService.insertSubAccount(shopUserDto);
    }

    /**
     * 跳转查看角色页面
     *
     * @return
     */
    @RequestMapping(value = "torole")
    public String toRole() {
        return "/shop/account/role.html";
    }

    /**
     * 查看角色页面数据
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "toroledata")
    @ResponseBody
    public SuccessDataTip toRoleData(@RequestParam Long id) {
        return subAccountService.toRoleData(id);
    }

    /**
     * 子账号重置密码
     *
     * @param id 子账号主键ID
     * @return SuccessMesTip
     */
    @RequestMapping(value = "resetpassword")
    @ResponseBody
    public SuccessMesTip resetPassword(@RequestParam Long id) {
        return subAccountService.resetPassword(id);
    }

}
