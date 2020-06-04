package cn.ibdsr.web.common.constant.dictmap.goodsdict;

import cn.ibdsr.web.common.constant.dictmap.base.AbstractDictMap;

/**
 * 商品类别日志数据字典
 *
 * @author XuZhipeng
 * @Date 2019-02-25 11:26:17
 */
public class GoodsCategoryDict extends AbstractDictMap {

    @Override
    public void init() {
        put("id", "类别名称");
        put("name", "类别名称");
        put("frontName", "前台展示名称");
        put("pid", "父类别");
        put("iconImg", "图标");
    }

    @Override
    protected void initBeWrapped() {
        putFieldWrapperMethodName("id","getGoodsCategoryName");
        putFieldWrapperMethodName("pid","getGoodsCategoryName");
    }
}
