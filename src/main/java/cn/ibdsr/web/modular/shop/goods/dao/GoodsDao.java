/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.dao;

import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 商家端商品管理Dao类
 * @Version: V1.0
 * @CreateDate: 2019/3/6 13:59
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/6      Zhujingrui               类说明
 *
 */
public interface GoodsDao {

    /**
     * @Description 根据goodsID查找goodsIntroId
     * @param goodsId
     * @return 返回goods_intro.id
     */
    Long selectGoodsIntroId(@Param("goodsId") Long goodsId);

    /**
     * @Description 根据goodsID查找商品基本属性
     * @param goodsId 商品id
     * @return
     */
    GoodsVO getGoods(@Param("goodsId") Long goodsId);

}
