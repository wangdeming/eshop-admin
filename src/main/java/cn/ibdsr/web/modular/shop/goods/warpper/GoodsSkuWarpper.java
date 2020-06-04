/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.ImageUtil;

import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/3/15 16:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/15      Zhujingrui               类说明
 *
 */
public class GoodsSkuWarpper extends BaseControllerWarpper {

    public GoodsSkuWarpper(Object obj) {
        super(obj);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.remove("createdTime");
        map.remove("modifiedTime");
        map.remove("createdUser");
        map.remove("modifiedUser");
        map.remove("isDeleted");
        map.remove("goodsId");
        // 商品图片
        map.put("img", ImageUtil.setImageURL(String.valueOf(map.get("img"))));
        // 商品价格
        map.put("price", AmountFormatUtil.amountFormat(map.get("price")));
        if (map.get("referPrice") != null) {
            map.put("referPrice", AmountFormatUtil.amountFormat(map.get("referPrice")));
        }


    }
}
