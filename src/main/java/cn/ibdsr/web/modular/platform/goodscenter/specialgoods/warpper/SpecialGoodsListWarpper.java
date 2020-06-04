package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.warpper;

import cn.ibdsr.core.base.warpper.BaseControllerWarpper;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.ImageUtil;

import java.util.Map;

/**
 * 商品列表的包装者
 *
 * @author XuZhipeng
 * @Date 2019-03-04 15:26:17
 */
public class SpecialGoodsListWarpper extends BaseControllerWarpper {

    public SpecialGoodsListWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {

        // 商品价格
        map.put("price", "￥" + AmountFormatUtil.amountFormat(map.get("price")));

        // 商品图片
        map.put("img", ImageUtil.setImageURL(String.valueOf(map.get("img"))));
    }

}
