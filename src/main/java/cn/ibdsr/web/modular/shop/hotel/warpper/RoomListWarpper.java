/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.ImageUtil;

import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/9 11:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/9      Zhujingrui               类说明
 *
 */
public class RoomListWarpper extends BaseControllerWarpper {

    public RoomListWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        // 商品价格
//        map.put("price", "￥" + AmountFormatUtil.amountFormat(map.get("price")));

        // 商品图片
        map.put("mainImg", ImageUtil.setImageURL(String.valueOf(map.get("mainImg"))));
    }
}
