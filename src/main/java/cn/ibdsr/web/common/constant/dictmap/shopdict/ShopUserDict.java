package cn.ibdsr.web.common.constant.dictmap.shopdict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 店铺用户字典
 *
 * @author XuZhipeng
 * @date 2019-03-05 15:01:00
 */
public class ShopUserDict extends AbstractDictMap {

    @Override
    public void init() {
        put("accountId", "店铺账号ID");
        put("shopId", "店铺ID");
        put("account", "店铺账号");
    }

    @Override
    protected void initBeWrapped() {

    }
}
