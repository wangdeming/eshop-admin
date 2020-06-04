package cn.ibdsr.web.modular.platform.accountmanage.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.node.ZTreeNode;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.modular.platform.accountmanage.service.MenuManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author xiaorongsheng
 * @Date 2018-04-23 16:34:31
 */
@Controller
@RequestMapping("/platform/menuManager")
public class MenuManagerController extends BaseController {
    @Resource
    MenuManagerService menuManagerService;

    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList(Integer platformType) {
        List<ZTreeNode> roleTreeList = menuManagerService.menuTreeList(platformType);
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(Long roleId, Integer platformType) {
        List<Long> menuIds = menuManagerService.getMenuIdsByRoleId(roleId);
        //如果菜单id为空，返回该平台类型对应的菜单树
        if (ToolUtil.isEmpty(menuIds)) {
            List<ZTreeNode> roleTreeList = menuManagerService.menuTreeList(ShiroKit.getUser().getPlatformType());
            return roleTreeList;
        } else {
            //根据条件获取菜单列表树
            List<ZTreeNode> roleTreeListByUserId = menuManagerService.menuTreeListByMenuIds(menuIds, platformType);
            return roleTreeListByUserId;
        }
    }

}
