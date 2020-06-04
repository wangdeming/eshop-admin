package cn.ibdsr.web.modular.platform.cash.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.common.constant.state.cash.WithdrawalStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.core.util.AmountFormatUtil;

import java.util.Map;

/**
 * @Description 提现列表数据包装类
 * @Version V1.0
 * @CreateDate 2019-04-23 14:41:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:41:11    XuZhipeng               类说明
 *
 */
public class WithdrawalListWarpper extends BaseControllerWarpper {

    public WithdrawalListWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        // 提现状态
        map.put("statusName", WithdrawalStatus.valueOf(Integer.valueOf(map.get("status").toString())));

        // 提现金额
        map.put("amount", AmountFormatUtil.amountFormat(map.get("amount")));

        // 店铺类型名称
        map.put("shopType", ShopType.valueOf(Integer.valueOf(map.get("shopType").toString())));
    }

}
