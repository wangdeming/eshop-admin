package cn.ibdsr.web.core.shiro.factory;

import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.Convert;
import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.PlatformType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserStatus;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.system.dao.MenuDao;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    //@Autowired
    //private UserMgrDao userMgrDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private ShopUserMapper shopUserMapper;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    /**
     * 根据账号获取登录用户信息
     *
     * @param account      账号
     * @param platformType 平台类型（1-系统平台；2-店铺；）
     * @return
     */
    @Override
    public ShiroUser shiroUser(String account, Integer platformType) {

        Map<String, Object> dataMap = null;
        if (PlatformType.PLATFORM.getCode() == platformType) {
            // 平台用户信息
            //dataMap = platformUser(account);
        } else {
            // 店铺用户信息
            dataMap = shopUser(account);
        }

        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setId((Long) dataMap.get("id"));
        //shiroUser.setShopId(dataMap.get("shopId") == null ? 0L : (Long) dataMap.get("shopId"));
        shiroUser.setAccount(String.valueOf(dataMap.get("account")));
        shiroUser.setName(String.valueOf(dataMap.get("name")));
        //shiroUser.setAvatar(String.valueOf(dataMap.get("avatar")));
        //shiroUser.setPassword(String.valueOf(dataMap.get("password")));
        //shiroUser.setSalt(String.valueOf(dataMap.get("salt")));
        shiroUser.setPlatformType((Integer) dataMap.get("platformType"));
        ShopData shopData = new ShopData();
        shopData.setShopId(dataMap.get("shopId") == null ? 0L : (Long) dataMap.get("shopId"));
        shopData.setAvatar(String.valueOf(dataMap.get("avatar")));
        shopData.setPassword(String.valueOf(dataMap.get("password")));
        shopData.setSalt(String.valueOf(dataMap.get("salt")));
        shiroUser.setData(shopData);

        // 获取角色信息
        if (dataMap.get("roleid") != null) {
            Long[] roleArray = Convert.toLongArray(String.valueOf(dataMap.get("roleid")));
            List<Long> roleList = new ArrayList<Long>();
            List<String> roleNameList = new ArrayList<String>();
            for (Long roleId : roleArray) {
                roleList.add(roleId);
                roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
            }
            shiroUser.setRoleList(roleList);                                            // 角色ID集合
            shiroUser.setRoleNames(roleNameList);
        }
        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByRoleId(Long roleId) {
        List<String> resUrls = menuDao.getResUrlsByRoleId(roleId);
        return resUrls;
    }

    @Override
    public String findRoleNameByRoleId(Long roleId) {
        return ConstantFactory.me().getSingleRoleTip(roleId);
    }

    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, String realmName) {
        ShopData shopData = (ShopData) shiroUser.getData();
        String credentials = shopData.getPassword();
        // 密码加盐处理
        String source = shopData.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName);
    }

    /**
     * 获取平台登录用户信息
     *
     * @param account 账号
     * @return
     */
    //private Map<String, Object> platformUser(String account) {
    //    User user = userMgrDao.getByAccount(account);
    //    // 账号不存在
    //    if (null == user) {
    //        throw new CredentialsException();
    //    }
    //
    //    // 账号被冻结
    //    if (user.getStatus() != ManagerStatus.OK.getCode()) {
    //        throw new LockedAccountException();
    //    }
    //
    //    Map<String, Object> returnMap = new HashMap<String, Object>();
    //    returnMap.put("id", user.getId());                              // 账号ID
    //    returnMap.put("account", user.getId());                         // 账号
    //    returnMap.put("name", user.getName());                          // 用户姓名
    //    returnMap.put("avatar", ImageUtil.setImageURL(user.getAvatar())); // 头像
    //    returnMap.put("password", user.getPassword());                  // 密码
    //    returnMap.put("salt", user.getSalt());                          // 密码盐
    //    returnMap.put("roleid", user.getRoleid());                      // 角色ID
    //    returnMap.put("platformType", PlatformType.PLATFORM.getCode()); // 平台类型：平台
    //    return returnMap;
    //}

    /**
     * 获取平台登录用户信息
     *
     * @param account 账号
     * @return
     */
    private Map<String, Object> shopUser(String account) {
        List<ShopUser> shopUserList = shopUserMapper.selectList(
                new EntityWrapper<ShopUser>()
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .eq("account", account)
                        .last("LIMIT 1"));
        // 账号不存在
        if (null == shopUserList || shopUserList.size() == 0) {
            throw new CredentialsException();
        }
        ShopUser shopUser = shopUserList.get(0);

        // 账号被冻结
        if (shopUser.getStatus() == ShopUserStatus.FREEZED.getCode()) {
            throw new LockedAccountException();
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("id", shopUser.getId());                              // 账号ID
        returnMap.put("shopId", shopUser.getShopId());                      // 店铺ID
        returnMap.put("account", shopUser.getId());                         // 账号
        returnMap.put("name", shopUser.getName());                          // 用户姓名]
        returnMap.put("avatar", ImageUtil.setImageURL(shopUser.getAvatar())); // 头像
        returnMap.put("password", shopUser.getPassword());                  // 密码
        returnMap.put("salt", shopUser.getSalt());                          // 密码盐
        returnMap.put("roleid", shopUser.getRoleid());                      // 角色ID
        returnMap.put("platformType", PlatformType.SHOP.getCode());         // 平台类型：店铺
        return returnMap;
    }
}
