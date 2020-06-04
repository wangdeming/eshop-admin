package cn.ibdsr.web.modular.platform.shop.info.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.common.constant.state.shop.ShopStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;

import java.util.Map;

/**
 * 店铺列表的包装者
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class ShopListWarpper extends BaseControllerWarpper {

    public ShopListWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        // 店铺类型名称
        map.put("shopTypeName", ShopType.valueOf(Integer.valueOf(map.get("type").toString())));

        // 店铺状态名称
        map.put("statusName", ShopStatus.valueOf(Integer.valueOf(map.get("status").toString())));

        map.remove("type");
    }

}
