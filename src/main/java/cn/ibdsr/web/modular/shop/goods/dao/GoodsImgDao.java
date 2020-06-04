/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商家端商品管理中图片管理Dao类
 * @Version: V1.0
 * @CreateDate: 2019/3/6 15:25
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/6      Zhujingrui               类说明
 *
 */
public interface GoodsImgDao {
    /**
     * @Description 根据图片id删除商品图片
     * @param id 商品图片id
     * @return
     */
    void delete(@Param("id") Long id);

}
