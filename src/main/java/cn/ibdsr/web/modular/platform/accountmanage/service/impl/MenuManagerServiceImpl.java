package cn.ibdsr.web.modular.platform.accountmanage.service.impl;

import cn.ibdsr.core.node.ZTreeNode;
import cn.ibdsr.web.modular.platform.accountmanage.dao.MenuManagerDao;
import cn.ibdsr.web.modular.platform.accountmanage.service.MenuManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理Service
 *
 * @author Zhujingrui
 * @Date 2019-02-28 10:14:56
 */
@Service
public class MenuManagerServiceImpl implements MenuManagerService {
    @Resource
    MenuManagerDao menuManagerDao;

    /**
     * 获取菜单列表树
     *@param platformType 平台类型
     * @return 返回该平台对应的菜单列表树
     */
    @Override
    public List<ZTreeNode> menuTreeList(Integer platformType) {
        return menuManagerDao.menuTreeList(platformType);
    }

    /**
     * 根据条件查询菜单
     *@param roleId 角色Id
     * @return 返回菜单id列表
     */
    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId){
        return menuManagerDao.getMenuIdsByRoleId(roleId);
    }

    /**
     * 根据条件获取菜单列表树
     *@param menuIds 菜单id列表
     *@param platformType 平台类型
     * @return 返回菜单列表树
     */
    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds, Integer platformType){
        return menuManagerDao.menuTreeListByMenuIds(menuIds,platformType);
    }

}
