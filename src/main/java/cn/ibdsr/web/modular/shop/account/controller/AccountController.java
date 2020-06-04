package cn.ibdsr.web.modular.shop.account.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.modular.shop.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "shopAccount")
@RequestMapping(value = "shop/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 我的账号
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "myaccount")
    public String myAccount(ModelMap modelMap) {
        accountService.myAccount(modelMap);
        return "/shop/account/myAccount.html";
    }

    /**
     * 跳转修改密码页
     *
     * @return
     */
    @RequestMapping(value = "toupdatepassword")
    public String toUpdatePassword() {
        return "/shop/account/updatePassword.html";
    }

    /**
     * 修改密码
     *
     * @param originalPassword
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "updatepassword")
    @ResponseBody
    public SuccessMesTip updatePassword(@RequestParam String originalPassword, @RequestParam String newPassword) {
        return accountService.updatePassword(originalPassword, newPassword);
    }

    /**
     * 修改默认密码
     *
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "updatedefaultpassword")
    @ResponseBody
    public SuccessMesTip updateDefaultPassword(@RequestParam String newPassword) {
        return accountService.updateDefaultPassword(newPassword);
    }

    /**
     * 跳转修改手机号页
     *
     * @return
     */
    @RequestMapping(value = "toupdatephone")
    public String toUpdatePhone() {
        return "/shop/account/updatePhone.html";
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping(value = "sendsms")
    @ResponseBody
    public SuccessDataTip sendSms(@RequestParam String phone) {
        return accountService.sendSms(phone);
    }

    /**
     * 验证短信验证码
     *
     * @param verifycode
     * @return
     */
    @RequestMapping(value = "verifycode")
    @ResponseBody
    public SuccessDataTip verifyCode(@RequestParam String verifycode) {
        return accountService.verifyCode(verifycode);
    }

    /**
     * 修改手机号
     *
     * @param phone
     * @param verifycode
     * @return
     */
    @RequestMapping(value = "updatephone")
    @ResponseBody
    public SuccessDataTip updatePhone(@RequestParam String phone, @RequestParam String verifycode) {
        return accountService.updatePhone(phone, verifycode);
    }

}
