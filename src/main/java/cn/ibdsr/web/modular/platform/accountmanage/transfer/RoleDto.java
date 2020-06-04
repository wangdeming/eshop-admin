/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.platform.accountmanage.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

/**
 * @Description: 角色管理DTO
 * @Version: V1.0
 * @CreateDate: 2019/2/27 15:59
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/2/27      Zhujingrui               类说明
 *
 */
public class RoleDto extends BaseDTO {
    private static final long serialVersionUID = 1L;
    /**
     * 序号
     */
    private Integer num;

    /**
     * 父角色id
     */
    private Long pid;

    /**
     * 角色名称
     */
    @Verfication(name = "角色名", notNull = true, maxlength = 15)
    private String name;

    /**
     * 权限列表
     */
    @Verfication(name = "权限选择", notNull = true)
    private String ids;


    /**
     * 店铺类型（1-特产店铺；2-酒店店铺；）
     */
    private Integer shopType;

    /**
     * 平台类型（1-平台；2-店铺；）
     */
    private Integer platformType;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}
