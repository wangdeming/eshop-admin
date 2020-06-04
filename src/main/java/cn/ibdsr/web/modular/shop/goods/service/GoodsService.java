package cn.ibdsr.web.modular.shop.goods.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
import org.apache.ibatis.annotations.Param;

public interface GoodsService {
    SuccessDataTip goodsCategoryList(int pid);

    SuccessTip insert(Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content);

    /**
     * @Description 方法描述
     * @param goodsId 商品ID
     * @param goods 商品对象
     * @param imageListJsonString 商品图片Json字符串
     * @param goodsSkuListJsonString 商品SKU的Json字符串
     * @param content 商品详情描述
     * @return
     */
    SuccessTip update(Long goodsId, Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content);

    /**
     * @Description 根据goodsID查找商品基本属性
     * @param goodsId 商品id
     * @return
     */
    GoodsVO getGoods(Long goodsId);

}
