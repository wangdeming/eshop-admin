package cn.ibdsr.web.modular.platform.accountmanage.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.Tip;
import cn.ibdsr.core.cache.CacheKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.cache.Cache;
import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.constant.state.SuccessStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.core.log.ShopLogObjectHolder;
import cn.ibdsr.web.modular.platform.accountmanage.service.RoleManageService;
import cn.ibdsr.web.modular.platform.accountmanage.warpper.RoleManageWarpper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * 商家端角色管理控制器
 *
 * @author Zhujingrui
 * @Date 2019-02-26 14:58:44
 */
@Controller
@RequestMapping("/platform/accountmanage")
public class RoleManageController extends BaseController {

    @Autowired
    RoleManageService roleManagerService;

    @Autowired
    RoleMapper roleMapper;

    private String PREFIX = "/platform/accountmanage/roleManage/";

    /**
     * 跳转到商家端角色管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "roleManage.html";
    }

    /**
     * 跳转到添加商家端角色管理
     */
    @RequestMapping("/roleManage_add")
    public String accountmanageAdd() {
        return PREFIX + "roleManage_add.html";
    }

    /**
     * 跳转到修改商家端角色管理
     */
    @RequestMapping("/roleManage_update/{roleManageId}")
    public String accountmanageUpdate(@PathVariable Integer roleManageId, Model model) {
        model.addAttribute("roleId", roleManageId);
        Role role = roleMapper.selectById(roleManageId);
        model.addAttribute("roleName", role.getName());
        model.addAttribute("shopType", role.getShopType());
        return PREFIX + "roleManage_edit.html";
    }

    /**
     * 跳转到查看角色管理
     */
    @RequestMapping("/roleManage_detail/{roleManageId}")
    public String roleManagerDetail(@PathVariable Long roleManageId, Model model) {
        model.addAttribute("roleId", roleManageId);
        Role role = roleMapper.selectById(roleManageId);
        model.addAttribute("roleName", role.getName());
        model.addAttribute("shopType", role.getShopType());
        return PREFIX + "roleManage_detail.html";
    }

    /**
     * 查看用户权限管理
     */
    @GetMapping("/subAccount_detail")
    public String subAccount_detail(@RequestParam("account") String account, @RequestParam("roleId") Long roleManagerId, Model model) {
        model.addAttribute("roleId", roleManagerId);
        model.addAttribute("account", account);
        Role role = roleMapper.selectById(roleManagerId);
        model.addAttribute("roleName", role.getName());
        return PREFIX + "subAccount_detail.html";
    }

    /**
     * 新增商家端角色管理
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam("name") String name, @RequestParam("shopType") Integer shopType, @RequestParam("ids") String ids) {
        //判断角色是否已经存在
        if (roleManagerService.isRoleExist(name, null) > 0) {
            throw new BussinessException(BizExceptionEnum.ROLE_IS_EXIST);
        }

        //添加角色和权限
        this.roleManagerService.addRole(name, shopType, ids);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除商家端角色管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long roleId) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员角色
        if (roleId.equals(Const.ADMIN_ROLE_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        //查询角色是否被其它用户使用，如果被使用则不能被删除。
        if (roleManagerService.countUserNum(roleId) > 0) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ROLEHASEUSER);
        }
        //查询角色是否被其它用户使用，如果被使用则不能被删除。
        if (roleManagerService.isUsed(roleId) > 0) {
            throw new BussinessException(BizExceptionEnum.DELETE_ROLE_ERROR);
        }

        //缓存被删除的角色名称
        ShopLogObjectHolder.me().set(ConstantFactory.me().getSingleRoleName(roleId));

        //删除角色
        roleManagerService.deleteRoleById(roleId);

        //删除缓存
        CacheKit.removeAll(Cache.CONSTANT);
        return new SuccessMesTip(SuccessStatus.DELETE_ROLE_SUCCESS);
    }

    /**
     * 修改商家端角色管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 提交权限时操作
     */
    @RequestMapping("/editRole")
    @ResponseBody
    public Tip setAuthority(@RequestParam("roleId") Long roleId, @RequestParam("name") String name, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //角色名的长度不能大于15
        if (name.length() > 15) {
            throw new BussinessException(BizExceptionEnum.ROLE_CHECK);
        }

        // 根据角色名 判断角色是否存在
        if (roleManagerService.isRoleExist(name, roleId) > 0) {
            throw new BussinessException(BizExceptionEnum.ROLE_IS_EXIST);
        }
        //更新角色名
        Role role = roleMapper.selectById(roleId);
        role.setName(name);
        role.updateById();

        //更新角色授权
        this.roleManagerService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }

    /**
     * 商家端角色管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    /**
     * 根据条件查询角色列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition, @RequestParam(required = true) Integer shopType) {
        //设置分页
        Page<Role> page = new PageFactory<Role>().defaultPage();
        //根据条件查询角色列表
        List<Map<String, Object>> roles = roleManagerService.selectRoles(page, condition, shopType);
        page.setRecords((List<Role>) new RoleManageWarpper(roles).warp());
        return super.packForBT(page);
    }


    @RequestMapping(value = "/selectRoleList")
    @ResponseBody
    public Object selectRoleList(@RequestParam("shopType") Integer shopType) {
        //根据店铺类型查看对应角色列表
        List<Map<String, Object>> mapList = roleManagerService.selectRoleList(shopType);
        return new SuccessDataTip(mapList);
    }
}
