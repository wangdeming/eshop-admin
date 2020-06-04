/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.common.constant.dictmap.accountmanagedict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/2/27 21:40
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/2/27      Zhujingrui               类说明
 *
 */
public class RoleDict extends AbstractDictMap {
    @Override
    public void init() {
        put("roleId","角色名称");
        put("num","角色排序序号");
        put("name","角色名称");
//        put("deptid","部门名称");
        put("tips","备注");
//        put("ids","资源名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("roleId","getSingleRoleName");
//        putFieldWrapperMethodName("ids","getMenuNames");
    }
}
