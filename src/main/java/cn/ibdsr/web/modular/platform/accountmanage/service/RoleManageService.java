package cn.ibdsr.web.modular.platform.accountmanage.service;

import cn.ibdsr.core.base.service.BaseService;
import cn.ibdsr.core.base.tips.ResultDTO;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.modular.platform.accountmanage.transfer.RoleDto;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 商家端角色管理Service
 *
 * @author Zhujingrui
 * @Date 2019-02-28 10:14:56
 */
public interface RoleManageService  extends BaseService<RoleDto, Role> {

    /**
     * 根据条件查询角色列表
     * @param condition 条件，非必须
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return 角色列表
     */
    List<Map<String, Object>> selectRoles(Page<Role> page, String condition, Integer shopType);

    /**
     * 查看角色是否被用户使用
     * @param roleId 角色Id
     * @return 返回该角色的用户数量
     */
    Integer countUserNum(Long roleId);

    /**
     * 删除角色名
     * @param roleId 角色Id
     * @return
     */
    void deleteRoleById(Long roleId);

    /**
     * 设置某个角色的权限
     * @param roleId 角色id
     * @param ids 权限的id
     */
    void setAuthority(Long roleId, String ids);

    /**
     * 添加角色
     * @param name     角色名
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @param ids      角色权限
     * @return
     */
    ResultDTO<Long> addRole(String name, Integer shopType, String ids);

    /**
     * 根据店铺类型查看对应角色列表
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<Map<String, Object>> selectRoleList(Integer shopType);

    /**
     * 查看角色是否已经使用了
     * @param roleId 角色Id
     * @return 返回该角色未删除的用户数量
     */
    Integer isUsed( Long roleId);

    /**
     * 判断角色是否存在
     * @param name 角色名
     * @param roleId 角色Id
     * @return
     */
    Integer isRoleExist(String name,Long roleId);

}

