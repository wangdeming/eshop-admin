package cn.ibdsr.web.modular.platform.cash.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.core.util.DateUtil;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.core.util.AmountFormatUtil;

import java.util.Date;
import java.util.Map;

/**
 * @Description 收益分成列表数据包装类
 * @Version V1.0
 * @CreateDate 2019-04-18 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-18 14:41:11    XuZhipeng               类说明
 *
 */
public class ProfitDisListWarpper extends BaseControllerWarpper {

    public ProfitDisListWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        // 店铺类型名称
        map.put("shopType", ShopType.valueOf(Integer.valueOf(map.get("shopType").toString())));

        // 创建时间
        map.put("createdTime", map.get("createdTime").toString().substring(0, 10));

        // 当前费率
        map.put("serviceRate", AmountFormatUtil.convertPercent(map.get("serviceRate")));

        // 变更后费率
        if (ToolUtil.isNotEmpty(map.get("changeServiceRate"))) {
            map.put("changeServiceRate", AmountFormatUtil.convertPercent(map.get("changeServiceRate")));
        }
    }

}
