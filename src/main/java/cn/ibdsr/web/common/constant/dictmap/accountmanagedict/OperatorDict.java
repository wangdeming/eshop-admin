/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.common.constant.dictmap.accountmanagedict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/2/27 22:11
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/2/27      Zhujingrui               类说明
 *
 */
public class OperatorDict extends AbstractDictMap {
    @Override
    public void init() {
        put("newPwd","用户名");
        put("phoneNumber","用户名");
        put("roleId","角色名称");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("newPwd","getCacheObject");
        putFieldWrapperMethodName("phoneNumber","getCacheObject");
        putFieldWrapperMethodName("roleId","getCacheObject");

    }
}
