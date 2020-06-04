package cn.ibdsr.web.modular.platform.accountmanage.service.impl;

import cn.ibdsr.core.base.service.impl.AbstractBaseService;
import cn.ibdsr.core.base.tips.ResultDTO;
import cn.ibdsr.core.util.Convert;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.RelationMapper;
import cn.ibdsr.web.common.persistence.dao.RoleMapper;
import cn.ibdsr.web.common.persistence.model.Relation;
import cn.ibdsr.web.common.persistence.model.Role;
import cn.ibdsr.web.modular.platform.accountmanage.dao.RoleManageDao;
import cn.ibdsr.web.modular.platform.accountmanage.transfer.RoleDto;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.platform.accountmanage.service.RoleManageService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 商家端角色管理Service
 *
 * @author Zhujingrui
 * @Date 2019-02-28 10:14:56
 */
@Service
public class RoleManageServiceImpl extends AbstractBaseService<RoleDto,Role> implements RoleManageService {

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleManageDao roleManageDao;

    @Autowired
    RelationMapper relationMapper;

    @Override
    public BaseMapper<Role> getMapper() {
        return roleMapper;
    }

    @Override
    public Role getConversionDO() {
        return new Role();
    }

    @Override
    public RoleDto getConversionDTO() {
        return new RoleDto();
    }

    @Override
    public void checkInsert(RoleDto roleDto) {

    }

    @Override
    public void checkUpdate(RoleDto roleDto) {

    }

    /**
     * 根据条件查询角色列表
     * @param condition 条件，非必须
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return 角色列表
     */
    @Override
    public List<Map<String, Object>> selectRoles(Page<Role> page, String condition, Integer shopType) {
        return roleManageDao.selectRoles(page, condition, shopType);
    }

    /**
     * 查看角色是否被用户使用
     * @param roleId 角色Id
     * @return 返回该角色的用户数量
     */
    @Override
    public Integer countUserNum(Long roleId) {
        return roleManageDao.countUserNum(roleId);
    }

    /**
     * 删除角色名
     * @param roleId 角色Id
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteRoleById(Long roleId) {
        //删除角色
        roleManageDao.deleteRoleById(roleId);

        // 删除该角色所有的权限
        roleManageDao.deleteRoleAuthById(roleId);
    }

    /**
     * 设置某个角色的权限
     * @param roleId 角色id
     * @param ids 权限的id
     */
    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Long roleId, String ids) {
        //判断角色权限是否为空
        if (StringUtils.isEmpty(ids)) {
            throw new BussinessException(BizExceptionEnum.IDS_IS_NULL);
        }

        // 删除该角色所有的权限
        this.roleManageDao.deleteRoleAuthById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    /**
     * 添加角色
     * @param name     角色名
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @param ids      角色权限
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public ResultDTO<Long> addRole(String name, Integer shopType, String ids) {
        RoleDto roleDto = roleInfoSet(name, shopType, ids); //角色基本信息设置
        ResultDTO<Long> resultDTO = insert(roleDto);
        this.setAuthority(resultDTO.getId(), ids); //插入角色权限
        return resultDTO;
    }

    /**
     * 角色基本信息设置
     * @param name     角色名
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @param ids      角色权限
     * @return
     */
    private RoleDto roleInfoSet(String name, Integer shopType, String ids) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName(name);
        roleDto.setShopType(shopType);
        roleDto.setIds(ids);
        roleDto.setNum(1);
        roleDto.setPid(0L);
        roleDto.setPlatformType(2);
        return roleDto;
    }

    /**
     * 查看所有角色列表
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return 角色列表
     */
    @Override
    public List<Map<String, Object>> selectRoleList(Integer shopType) {
        return roleManageDao.selectRoleList(shopType);
    }

    /**
     * 查看角色是否已经使用了
     * @param roleId 角色Id
     * @return 返回该角色未删除的用户数量
     */
    @Override
    public Integer isUsed(Long roleId) {
        return roleManageDao.isUsed(roleId);
    }

    /**
     * 判断角色是否存在
     * @param name 角色名
     * @param roleId 角色Id
     * @return
     */
    @Override
    public Integer isRoleExist(String name, Long roleId) {
        return roleManageDao.isRoleExist(name, roleId);
    }

}
