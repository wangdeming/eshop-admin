package cn.ibdsr.web.modular.shop.account.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.cache.CacheKit;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.cache.Cache;
import cn.ibdsr.web.common.constant.state.MessageStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopUserAccountType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.core.http.HttpUtils;
import cn.ibdsr.web.modular.shop.account.service.AccountService;
import cn.ibdsr.web.modular.shop.account.transfer.SmsgDto;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service(value = "shopAccountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void myAccount(ModelMap modelMap) {
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        JSONObject account = new JSONObject();
        if (StringUtils.isNotBlank(shopUser.getAccountType())) {
            account.put("accountTypeName", ShopUserAccountType.valueOf(Integer.valueOf(shopUser.getAccountType())));
        }
        account.put("account", shopUser.getAccount());
        account.put("name", shopUser.getName());
        account.put("phone", shopUser.getPhone());
        if (StringUtils.isNotBlank(shopUser.getRoleid())) {
            List<String> roleidList = Arrays.asList(StringUtils.split(shopUser.getRoleid(), ","));
            StringBuilder roleNames = new StringBuilder();
            for (String roleid : roleidList) {
                Role role = roleMapper.selectById(roleid);
                if (role != null) {
                    if (roleidList.indexOf(roleid) == 0) {
                        roleNames = new StringBuilder(role.getName());
                    } else {
                        roleNames.append(",").append(role.getName());
                    }
                }
            }
            account.put("roleNames", roleNames);
        }
        modelMap.put("account", account);
    }

    private SuccessMesTip updatePwd(String newPassword) {
        boolean isMatch = Pattern.matches(CheckUtil.PASSWORD, newPassword);
        if (!isMatch) {
            throw new BussinessException(BizExceptionEnum.PASSWORD_FORMAT_ERROR);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        //主账户不能修改为初始密码，初始密码为法人身份证后6位
        if (StringUtils.equals(shopUser.getAccountType(), String.valueOf(ShopUserAccountType.PARENT.getCode()))) {
            Shop shop = shopMapper.selectById(shopUser.getShopId());
            if (shop == null || shop.getIdentityId() == null) {
                throw new BussinessException(BizExceptionEnum.DATA_ERROR);
            }
            if (StringUtils.equals(newPassword, StringUtils.substring(shop.getIdentityId(), shop.getIdentityId().length() - 6, shop.getIdentityId().length()))) {
                throw new BussinessException(BizExceptionEnum.PASSWORD_DEFAULT);
            }
        }
        //子账号不能修改为默认密码
        if (StringUtils.equals(shopUser.getAccountType(), String.valueOf(ShopUserAccountType.SUB.getCode()))) {
            if (StringUtils.equals(newPassword, Const.DEFAULT_PASSWORD)) {
                throw new BussinessException(BizExceptionEnum.PASSWORD_DEFAULT);
            }
        }
        ShopUser shopUser1 = new ShopUser();
        shopUser1.setId(shiroUser.getId());
        shopUser1.setSalt(ShiroKit.getRandomSalt(5));
        shopUser1.setPassword(ShiroKit.md5(newPassword, shopUser1.getSalt()));
        shopUser1.setModifiedUser(shiroUser.getId());
        shopUser1.setModifiedTime(new Date());
        shopUserMapper.updateById(shopUser1);
        return new SuccessMesTip("密码修改成功");
    }

    @Override
    public SuccessMesTip updatePassword(String originalPassword, String newPassword) {
        if (StringUtils.equals(originalPassword, newPassword)) {
            throw new BussinessException(BizExceptionEnum.PASSWORD_REPEAT);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        if (!StringUtils.equals(shopUser.getPassword(), ShiroKit.md5(originalPassword, shopUser.getSalt()))) {
            throw new BussinessException(BizExceptionEnum.ORIGINAL_PASSWORD_ERROR);
        }
        return updatePwd(newPassword);
    }

    @Override
    public SuccessMesTip updateDefaultPassword(String newPassword) {
        return updatePwd(newPassword);
    }

    @Override
    public SuccessDataTip sendSms(String phone) {
        boolean isMatch = CheckUtil.isMobile(phone);
        if (!isMatch) {
            throw new BussinessException(BizExceptionEnum.MOBILE_FORMAT_ERROR);
        }
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("telephone", phone);
        SmsgDto smsgDto = HttpUtils.sendPostMsg(paramMap, Const.ORDER_CODE_URL);
        LOGGER.info(JSONObject.toJSONString(smsgDto));
        if (MessageStatus.Ok.getCode().equals(smsgDto.getCode())) {
            //验证码放到缓存中
            CacheKit.put(Cache.MESSAGE, phone, smsgDto.getData().getVerifyCode());
        } else {
            throw new BussinessException(BizExceptionEnum.SMS_CODE_SEND_FILE);
        }
        SuccessDataTip successDataTip = new SuccessDataTip(null);
        successDataTip.setMessage("验证码发送成功");
        return successDataTip;
    }

    @Override
    public SuccessDataTip verifyCode(String verifycode) {
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        String verifycode1 = CacheKit.get(Cache.MESSAGE, shopUser.getPhone());
        if (StringUtils.equals(verifycode, verifycode1)) {
            SuccessDataTip successDataTip = new SuccessDataTip(null);
            successDataTip.setMessage("验证码正确");
            return successDataTip;
        } else {
            throw new BussinessException(BizExceptionEnum.SMS_CODE_ERROR);
        }
    }

    @Override
    public SuccessDataTip updatePhone(String phone, String verifycode) {
        boolean isMatch = CheckUtil.isMobile(phone);
        if (!isMatch) {
            throw new BussinessException(BizExceptionEnum.MOBILE_FORMAT_ERROR);
        }
        String verifycode1 = CacheKit.get(Cache.MESSAGE, phone);
        if (StringUtils.equals(verifycode, verifycode1)) {
            ShiroUser shiroUser = ShiroKit.getUser();
            ShopUser shopUser = new ShopUser();
            shopUser.setId(shiroUser.getId());
            shopUser.setPhone(phone);
            shopUser.setModifiedUser(shiroUser.getId());
            shopUser.setModifiedTime(new Date());
            shopUserMapper.updateById(shopUser);
            SuccessDataTip successDataTip = new SuccessDataTip(null);
            successDataTip.setMessage("手机号修改成功");
            return successDataTip;
        } else {
            throw new BussinessException(BizExceptionEnum.SMS_CODE_ERROR);
        }
    }

}
