/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.core.util.ImageUtil;

import java.util.Map;

/**
 * @Description: 商品图片的包装类
 * @Version: V1.0
 * @CreateDate: 2019/3/15 16:15
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/15      Zhujingrui               类说明
 *
 */
public class GoodsImgWarpper extends BaseControllerWarpper {
    public GoodsImgWarpper(Object obj) {
        super(obj);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.remove("createdUser");
        map.remove("createdTime");
        map.remove("isDeleted");
        map.remove("goodsId");
        // 商品图片
        map.put("img", ImageUtil.setImageURL(String.valueOf(map.get("img"))));
    }
}
