package cn.ibdsr.web.modular.shop.goods.service;

import cn.ibdsr.web.modular.shop.goods.transfer.GoodsListVO;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 商品列表管理Service
 *
 * @author XuZhipeng
 * @Date 2019-03-04 15:26:18
 */
public interface IGoodsListService {

    /**
     * 分页查询商品列表
     *
     * @param page
     * @param goodsQueryDTO 商品查询对象DTO
     *                      status 商品状态（1-销售中；2-已售罄；3-仓库中；）
     *                      goodsName 商品名称
     *                      minPrice 价格范围最低
     *                      maxPrice 价格范围最高
     *                      minSale 销量范围最低
     *                      maxSale 销量范围最高
     * @return
     */
    List<Map<String,Object>> list(Page<GoodsListVO> page, GoodsQueryDTO goodsQueryDTO);

    /**
     * 批量更新商品状态
     *
     * @param goodsIds 商品ID数组
     * @param statusCode 商品状态（1-上架；2-下架；）
     */
    void batchUpdateGoodsStatus(Long[] goodsIds, Integer statusCode);

    /**
     * 被系统下架的商品批量申请重新上架
     *
     * @param goodsIds 商品ID数组
     */
    void applyGoodsOnShelf(Long[] goodsIds);

    /**
     * 更新商品序列号
     *
     * @param goodsId 商品ID
     * @param sequence 序列号
     */
    void updateSequence(Long goodsId, Integer sequence);

    /**
     * 删除商品
     * @param goodsId 商品ID
     */
    void deleteGoods(Long[] goodsId);
}
