package cn.ibdsr.web.modular.platform.accountmanage.dao;

import cn.ibdsr.web.common.persistence.model.Role;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商家端角色管理Dao
 *
 * @author Zhujingrui
 * @Date 2019-02-26 14:58:44
 */
public interface RoleManageDao {

    /**
     * 根据条件查询角色列表
     * @param condition 条件，非必须
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return 角色列表
     */
    List<Map<String, Object>> selectRoles(@Param("page") Page<Role> page, @Param("condition") String condition, @Param("shopType") Integer shopType);

    /**
     * 查看角色是否被用户使用
     * @param roleId 角色Id
     * @return 返回该角色的用户数量
     */
    Integer countUserNum(@Param("roleId") Long roleId);

    /**
     * 删除角色名
     * @param roleId 角色Id
     * @return
     */
    Integer deleteRoleById(@Param("roleId") Long roleId);

    /**
     * 删除某个角色的所有权限
     * @param roleId 角色Id
     * @return
     */
    Integer deleteRoleAuthById(@Param("roleId") Long roleId);

    /**
     * 根据店铺类型查看对应角色列表
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return 返回角色列表
     */
    List<Map<String, Object>> selectRoleList(@Param("shopType") Integer shopType);

    /**
     * 查看角色是否已经使用了
     * @param roleId 角色Id
     * @return 返回该角色未删除的用户数量
     */
    Integer isUsed(@Param("roleId") Long roleId);

    /**
     * 判断角色是否存在
     * @param name 角色名
     * @param roleId 角色Id
     * @return 返回根据条件查询的角色数量
     */
    Integer isRoleExist(@Param("name") String name, @Param("roleId") Long roleId);


}
