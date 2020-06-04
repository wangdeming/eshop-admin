/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/3/6 16:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/6      Zhujingrui               类说明
 *
 */
public interface GoodsIntroDao {

    /**
     * @Description 更新商品详情描述
     * @param content 商品详情描述新的值
     * @param id goods_intro.id，商品详情表id
     * @return null
     */
    void updateGoodsIntro(@Param("content") String content,@Param("id") Long id);

    /**
     * @Description 根据商品id获取商品详情描述信息
     * @param goodsId 商品id
     * @return 商品详情描述信息
     */
    String getIntroContentByGoodsId(@Param("goodsId") Long goodsId);
}
