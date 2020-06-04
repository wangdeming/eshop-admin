package cn.ibdsr.web.common.constant.dictmap.goodsdict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 商品信息日志数据字典
 *
 * @author XuZhipeng
 * @Date 2019-03-05 11:26:17
 */
public class GoodsInfoDict extends AbstractDictMap {

    @Override
    public void init() {
        put("goodsIds", "商品ID");
        put("sequence", "序列号");
    }

    @Override
    protected void initBeWrapped() {

    }
}
