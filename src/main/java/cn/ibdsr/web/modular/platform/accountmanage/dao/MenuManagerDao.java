package cn.ibdsr.web.modular.platform.accountmanage.dao;

import cn.ibdsr.core.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理Dao
 *
 * @author Zhujingrui
 * @Date 2019-02-26 14:58:44
 */
public interface MenuManagerDao {

    /**
     * 根据条件查询菜单
     *@param roleId 角色Id
     * @return 返回菜单id列表
     */
    List<Long> getMenuIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取菜单列表树
     *@param platformType 平台类型
     * @return 返回该平台对应的菜单列表树
     */
    List<ZTreeNode> menuTreeList(@Param("platformType") Integer platformType);

    /**
     * 根据条件获取菜单列表树
     *@param menuIds 菜单id列表
     *@param platformType 平台类型
     * @return 返回菜单列表树
     */
    List<ZTreeNode> menuTreeListByMenuIds(@Param("list") List<Long> menuIds, @Param("platformType") Integer platformType);



}
