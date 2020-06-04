package cn.ibdsr.web.modular.shop.account.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.Sex;
import cn.ibdsr.web.common.constant.state.shop.RoleShopType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserAccountType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RelationMapper;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Relation;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.modular.shop.account.dao.SubAccountDao;
import cn.ibdsr.web.modular.shop.account.service.SubAccountService;
import cn.ibdsr.web.modular.shop.account.transfer.ShopUserDto;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class SubAccountServiceImpl implements SubAccountService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private SubAccountDao subAccountDao;

    @Override
    public JSONObject subAccountList() {
        Wrapper wrapper = new EntityWrapper();
        wrapper.setSqlSelect("id", "account", "name", "roleid", "phone", "created_time", "status");
        wrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        ShiroUser shiroUser = ShiroKit.getUser();
        wrapper.eq("pid", shiroUser.getId());
        List<ShopUser> shopUserList = shopUserMapper.selectList(wrapper);
        List<JSONObject> list = JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(shopUserList, "yyyy-MM-dd HH:mm:ss"), JSONObject.class);
        for (JSONObject jsonObject : list) {
            List<String> roleidList = Arrays.asList(StringUtils.split(jsonObject.getString("roleid"), ","));
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
            jsonObject.put("roleNames", roleNames);
            jsonObject.put("statusName", ShopUserStatus.valueOf(jsonObject.getIntValue("status")));
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("rows", list);
        returnJson.put("total", list.size());
        return returnJson;
    }

    @Override
    public SuccessTip freezed(Long id, Integer status) {
        ShopUser shopUser = new ShopUser();
        shopUser.setId(id);
        shopUser.setStatus(status);
        shopUserMapper.updateById(shopUser);
        return new SuccessTip();
    }

    @Override
    public SuccessTip delete(Long id) {
        ShopUser shopUser = new ShopUser();
        shopUser.setId(id);
        shopUser.setIsDeleted(IsDeleted.DELETED.getCode());
        shopUserMapper.updateById(shopUser);
        return new SuccessTip();
    }

    @Override
    public SuccessDataTip toInsertSubAccountData() {
        JSONObject returnJson = new JSONObject();
        JSONObject paramJson = new JSONObject();
        List<JSONObject> genderList = new ArrayList<>();
        JSONObject male = new JSONObject();
        male.put("code", Sex.MALE.getCode());
        male.put("message", Sex.MALE.getMessage());
        genderList.add(male);
        JSONObject female = new JSONObject();
        female.put("code", Sex.FEMALE.getCode());
        female.put("message", Sex.FEMALE.getMessage());
        genderList.add(female);
        returnJson.put("genderList", genderList);
        Wrapper wrapper = new EntityWrapper();
        wrapper.setSqlSelect("id", "name");
        List<Long> roleIdList = ShiroKit.getUser().getRoleList();
        Role crole = roleMapper.selectById(roleIdList.get(0));
        wrapper.eq("shop_type", crole.getShopType());
        List<Role> roleList = roleMapper.selectList(wrapper);
        List<JSONObject> roleJsonList = JSONObject.parseArray(JSONObject.toJSONString(roleList), JSONObject.class);
        for (JSONObject role : roleJsonList) {
            Wrapper wrapper1 = new EntityWrapper();
            wrapper1.eq("roleid", role.getString("id"));
            List<Relation> relationList = relationMapper.selectList(wrapper1);
            if (relationList.size() > 0) {
                List<Long> menuidList = new ArrayList<>();
                for (Relation relation : relationList) {
                    if (relation.getMenuid() != null) {
                        menuidList.add(relation.getMenuid());
                    }
                }
                if (menuidList.size() > 0) {
                    paramJson.clear();
                    paramJson.put("sql", "and m1.id in (" + StringUtils.join(menuidList, ",") + ")");
                    List<JSONObject> menuList = subAccountDao.listRole(paramJson);
                    for (JSONObject menu : menuList) {
                        menu.put("isOpen", menu.getBooleanValue("isOpen"));
                        menu.put("open", menu.getBooleanValue("isOpen"));
                        menu.put("checked", menu.getBooleanValue("checked"));
                    }
                    role.put("menuList", menuList);
                }
            }
        }
        returnJson.put("roleList", roleJsonList);
        return new SuccessDataTip(returnJson);
    }

    @Override
    public SuccessTip insertSubAccount(ShopUserDto shopUserDto) {
        if (Pattern.matches(CheckUtil.MOBILE_PHONE, shopUserDto.getAccount())) {
            throw new BussinessException(BizExceptionEnum.ACCOUNT_FORMAT_ERROR);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        Wrapper wrapper = new EntityWrapper();
        wrapper.eq("pid", shiroUser.getId());
        wrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        Integer count = shopUserMapper.selectCount(wrapper);
        if (count >= 20) {
            throw new BussinessException(BizExceptionEnum.SUB_ACCOUNT_MAX);
        }
        StaticCheck.check(shopUserDto);
        ShopUser param = new ShopUser();
        param.setAccount(shopUserDto.getAccount());
        ShopUser shopUser1 = shopUserMapper.selectOne(param);
        if (shopUser1 != null) {
            throw new BussinessException(BizExceptionEnum.ACCOUNT_REPEAT);
        }
        ShopUser shopUser = JSONObject.parseObject(JSONObject.toJSONString(shopUserDto), ShopUser.class);
        shopUser.setShopId(((ShopData) shiroUser.getData()).getShopId());
        shopUser.setSalt(ShiroKit.getRandomSalt(5));
        shopUser.setPassword(ShiroKit.md5(shopUser.getPassword(), shopUser.getSalt()));
        shopUser.setAccountType(String.valueOf(ShopUserAccountType.SUB.getCode()));
        shopUser.setStatus(ShopUserStatus.NOACTIVE.getCode());
        shopUser.setCreatedUser(shiroUser.getId());
        shopUser.setPid(shiroUser.getId());
        shopUser.setCreatedTime(new Date());
        shopUserMapper.insert(shopUser);
        return new SuccessTip();
    }

    @Override
    public SuccessDataTip toRoleData(Long id) {
        JSONObject returnJson = new JSONObject();
        ShopUser shopUser = shopUserMapper.selectById(id);
        Role role = roleMapper.selectById(shopUser.getRoleid());
        //去掉多余字段，只保留id、code等5个字段
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Role.class, "id", "name", "shopType");
        JSONObject roleJson = JSONObject.parseObject(JSONObject.toJSONString(role, filter), JSONObject.class);
        roleJson.put("shopTypeName", RoleShopType.valueOf(role.getShopType()));
        returnJson.put("role", roleJson);
        Wrapper wrapper1 = new EntityWrapper();
        wrapper1.eq("roleid", shopUser.getRoleid());
        List<Relation> relationList = relationMapper.selectList(wrapper1);
        List<Long> menuidList = new ArrayList<>();
        returnJson.put("menuidList", menuidList);
        for (Relation relation : relationList) {
            menuidList.add(relation.getMenuid());
        }
        JSONObject paramJson = new JSONObject();
        List<JSONObject> menuList = subAccountDao.listRole(paramJson);
        for (JSONObject menu : menuList) {
            menu.put("isOpen", menu.getBooleanValue("isOpen"));
            menu.put("open", menu.getBooleanValue("isOpen"));
            menu.put("checked", menu.getBooleanValue("checked"));
            for (Long menuid : menuidList) {
                if (menuid.equals(menu.getLong("id"))) {
                    menu.put("checked", Boolean.TRUE);
                }
            }
        }
        returnJson.put("menuList", menuList);
        return new SuccessDataTip(returnJson);
    }

    /**
     * 子账号重置密码
     *
     * @param id 子账号主键ID
     * @return SuccessMesTip
     */
    @Override
    public SuccessMesTip resetPassword(Long id) {
        ShopUser shopUser = new ShopUser();
        shopUser.setId(id);
        shopUser.setSalt(ShiroKit.getRandomSalt(5));
        shopUser.setPassword(ShiroKit.md5(Const.DEFAULT_PASSWORD, shopUser.getSalt()));
        shopUser.setModifiedUser(ShiroKit.getUser().getId());
        shopUser.setModifiedTime(new Date());
        shopUser.updateById();
        return new SuccessMesTip("重置成功，密码为" + Const.DEFAULT_PASSWORD);
    }

}
