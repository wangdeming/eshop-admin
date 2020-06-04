package cn.ibdsr.web.common.constant.factory;

import cn.ibdsr.web.common.constant.cache.Cache;
import cn.ibdsr.web.common.constant.cache.CacheKey;
import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.config.properties.UrlProperties;
import cn.ibdsr.web.config.properties.WXpayProperties;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author fengshuonan
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     *
     * @author stylefeng
     * @Date 2017/5/9 23:41
     */
    //String getUserNameById(Long userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    //String getUserAccountById(Long userId);

    /**
     * 通过角色ids获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    String getSingleRoleName(Long roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    String getSingleRoleTip(Long roleId);

    /**
     * 获取部门名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    String getDeptName(Long deptId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取字典名称
     */
    String getDictName(Long dictId);

    /**
     * 获取通知标题
     */
    String getNoticeTitle(Long dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 获取性别名称
     */
    String getSexName(Integer sex);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 查询字典
     */
    List<Dict> findInDict(Long id);

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

    /**
     * 获取子部门id
     */
    List<Long> getSubDeptId(Long deptid);

    /**
     * 获取所有父部门id
     */
    List<Long> getParentDeptIds(Long deptid);

    /**
     * 通过ID获取省市县区街道地区名称
     *
     * @param areaId
     * @return
     */
    String getAreaNameById(Long areaId);

    /**
     * 根据字典名称获取字典集合
     *
     * @param name 字典名称
     * @return
     */
    List<Dict> getSubListByName(String name);

    /**
     * 获取店铺类型
     */
    List<Dict> getShopTypeList();

    /**
     * URL对象
     */
    UrlProperties getUrls();

    /**
     * 根据商品类别ID获取商品类型名称
     *
     * @param categoryId 商品类别ID
     * @return
     */
    String getGoodsCategoryName(Long categoryId);

    /**
     * 获取微信支付服务的配置信息
     */
    WXpayProperties getWXpayProperties();
}
