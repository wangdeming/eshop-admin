package cn.ibdsr.web.modular.shop.account.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.modular.shop.account.transfer.ShopUserDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;

public interface SubAccountService {
    JSONObject subAccountList();

    SuccessTip freezed(Long id, Integer status);

    SuccessTip delete(Long id);

    SuccessDataTip toInsertSubAccountData();

    SuccessTip insertSubAccount(ShopUserDto shopUserDto);

    SuccessDataTip toRoleData(Long id);

    SuccessMesTip resetPassword(@RequestParam Long id);
}
