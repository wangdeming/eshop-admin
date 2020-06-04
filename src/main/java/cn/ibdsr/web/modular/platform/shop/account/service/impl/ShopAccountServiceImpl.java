package cn.ibdsr.web.modular.platform.shop.account.service.impl;

import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.PlatformType;
import cn.ibdsr.web.common.constant.state.shop.ShopStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserAccountType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.modular.platform.shop.account.dao.ShopAccountDao;
import cn.ibdsr.web.modular.platform.shop.account.service.IShopAccountService;
import cn.ibdsr.web.modular.platform.shop.account.transfer.ShopAccountVO;
import cn.ibdsr.web.modular.platform.shop.info.service.IShopInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺账户管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
@Service
public class ShopAccountServiceImpl implements IShopAccountService {

    @Autowired
    private ShopAccountDao shopAccountDao;

    @Autowired
    private IShopInfoService shopInfoService;

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 开通账户
     *
     * @param shopId 店铺ID
     * @param account 账户名称
     */
    @Override
    @Transactional
    public void openAccount(Long shopId, String account) {
        // 校验账户名称是否重复
        checkAccount(account);

        // 校验店铺ID并获取店铺信息
        Shop shop = checkShopId(shopId);

        // 判断该店铺是否未开通账号
        if (ShopStatus.UNACCOUNT.getCode() != shop.getStatus()) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_NOACCOUNT);
        }

        ShopUser shopUser = new ShopUser();
        shopUser.setAccount(account);                                       // 账号名
        shopUser.setSalt(ShiroKit.getRandomSalt(Const.SALT_LENGTH));        // 密码盐
        // 设置账号密码 默认身份证后六位
        String password = shop.getIdentityId().substring(shop.getIdentityId().length() - 6);
        shopUser.setPassword(ShiroKit.md5(password, shopUser.getSalt()));
        shopUser.setStatus(ShopUserStatus.NOACTIVE.getCode());              // 账号状态，默认为未激活
        shopUser.setShopId(shopId);                                         // 关联的店铺ID
        shopUser.setName(shop.getLegalPerson());                            // 设置用户姓名
        shopUser.setSex(shop.getSex());                                     // 设置性别
        shopUser.setPlatformType(PlatformType.SHOP.getCode());              // 账号所属平台ID
        shopUser.setPid(0L);                                                // 父账号ID（主账号为0）
        shopUser.setPhone(shop.getPhone());                                 // 手机号码
        shopUser.setRoleid(ShopType.HOTEL.getCode() == shop.getType() ? Const.HOTEL_MASTER_ROLE : Const.SHOP_MASTER_ROLE);  // 默认角色
        shopUser.setIsDeleted(IsDeleted.NORMAL.getCode());
        shopUser.setCreatedTime(new Date());
        shopUser.setCreatedUser(ShiroKit.getUser().getId());
        shopUser.insert();

        // 更新店铺状态为正常营业
        shopInfoService.updateShopStatus(shopId, ShopStatus.NORMAL.getCode());
    }

    /**
     * 查询未开通账号的店铺列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listNoAccountShops() {
        List<Map<String, Object>> resultList = shopMapper.selectMaps(
                new EntityWrapper<Shop>()
                        .setSqlSelect("id, name")
                        .eq("status", ShopStatus.UNACCOUNT.getCode())
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        return resultList;
    }

    /**
     * 根据店铺ID查询新增主账号页面需要的信息
     *
     * @param shopId 店铺ID
     * @return
     */
    @Override
    public Map<String, Object> getShopInfoById(Long shopId) {
        // 校验店铺ID并获取店铺信息
        Shop shop = checkShopId(shopId);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("shopType", ShopType.valueOf(shop.getType()));    // 店铺类型
        resultMap.put("legalPerson", shop.getLegalPerson());            // 法人姓名
        resultMap.put("address", shop.getAddress());                    // 店铺地址
        return resultMap;
    }

    /**
     * 获取店铺账号列表
     *
     * @param condition 搜索关键字（店铺名/账户名/手机号）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    @Override
    public List<ShopAccountVO> list(String condition, Integer shopType) {
        List<ShopAccountVO> list = shopAccountDao.list(condition, shopType);
        for (ShopAccountVO shopAccount : list) {
            shopAccount.setLevel(ShopUserAccountType.valueOf(Integer.valueOf(shopAccount.getLevel()))); // 账户级别
            shopAccount.setShopType(ShopType.valueOf(Integer.valueOf(shopAccount.getShopType())));      // 店铺类型
            shopAccount.setStatusName(ShopUserStatus.valueOf(shopAccount.getStatus()));                 // 账号状态
        }
        return list;
    }

    /**
     * 查询店铺账号详情
     *
     * @param accountId 店铺账号ID
     * @return
     */
    @Override
    public Map<String, Object> getAccountInfo(Long accountId) {
        // 查询店铺账户信息
        ShopUser account = shopUserMapper.selectById(accountId);
        if (account == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_IS_NOT_EXIST);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("account", account.getAccount());                            // 账户号
        result.put("phone", account.getPhone());                                // 手机号码

        // 查询店铺信息
        Shop shop = shopMapper.selectById(account.getShopId());
        if (shop == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_EXIST);
        }
        result.put("shopId", shop.getId());                                     // 店铺ID
        result.put("shopName", shop.getName());                                 // 店铺类型
        result.put("legalPerson", shop.getLegalPerson());                       // 法人姓名
        result.put("shopType", ShopType.valueOf(shop.getType()));               // 店铺类型
        result.put("address", shop.getAddress());                               // 店铺地址
        return result;
    }

    /**
     * 校验店铺ID并获取店铺信息
     *
     * @param shopId 店铺ID
     * @return
     */
    private Shop checkShopId(Long shopId) {
        if (ToolUtil.isEmpty(shopId)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ID_IS_NULL);
        }

        Shop shop = shopMapper.selectById(shopId);
        if (shop == null) {
            throw new BussinessException(BizExceptionEnum.SHOP_IS_NOT_EXIST);
        }
        return shop;
    }

    /**
     * 校验账户名称是否重复
     *
     * @param account 账户名称
     */
    private void checkAccount(String account) {
        // 校验账户名格式
        if (!CheckUtil.checkReg(cn.ibdsr.web.core.check.CheckUtil.ACCOUNT, account)) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_NOT_FORMAT);
        }

        List<ShopUser> shopUserList = shopUserMapper.selectList(
                new EntityWrapper<ShopUser>().eq("account", account).eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = shopUserList != null && shopUserList.size() > 0;
        if (isExist) {
            throw new BussinessException(BizExceptionEnum.SHOP_ACCOUNT_IS_EXIST);
        }
    }
}
