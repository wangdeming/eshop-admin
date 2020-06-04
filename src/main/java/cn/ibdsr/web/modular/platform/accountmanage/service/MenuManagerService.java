package cn.ibdsr.web.modular.platform.accountmanage.service;


import cn.ibdsr.core.node.ZTreeNode;

import java.util.List;

/**
 * 菜单管理Service
 *
 * @author Zhujingrui
 * @Date 2019-02-28 10:14:56
 */
public interface MenuManagerService {

    /**
     * 获取菜单列表树
     *@param platformType 平台类型
     * @return 返回该平台对应的菜单列表树
     */
    List<ZTreeNode> menuTreeList(Integer platformType);

    /**
     * 根据条件查询菜单
     *@param roleId 角色Id
     * @return 返回菜单id列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 根据条件获取菜单列表树
     *@param menuIds 菜单id列表
     *@param platformType 平台类型
     * @return 返回菜单列表树
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds, Integer platformType);

}
