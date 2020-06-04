package cn.ibdsr.web.common.constant.dictmap.shopdict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 店铺字典
 *
 * @author XuZhipeng
 * @date 2019-03-05 15:01:00
 */
public class ShopInfoDict extends AbstractDictMap {

    @Override
    public void init() {
        put("shopId", "店铺ID");
    }

    @Override
    protected void initBeWrapped() {

    }
}
