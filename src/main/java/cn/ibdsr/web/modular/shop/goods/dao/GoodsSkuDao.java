/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/3/19 9:05
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/19      Zhujingrui               类说明
 *
 */
public interface GoodsSkuDao {

    void delete(@Param("goodsId") Long goodsId);
}
