package cn.ibdsr.web.modular.shop.account.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import org.springframework.ui.ModelMap;

public interface AccountService {
    void myAccount(ModelMap modelMap);

    SuccessMesTip updatePassword(String originalPassword, String newPassword);

    SuccessMesTip updateDefaultPassword(String newPassword);

    SuccessDataTip sendSms(String phone);

    SuccessDataTip verifyCode(String verifycode);

    SuccessDataTip updatePhone(String phone, String verifyCode);
}
