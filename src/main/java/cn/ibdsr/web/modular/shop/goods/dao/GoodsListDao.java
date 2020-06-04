package cn.ibdsr.web.modular.shop.goods.dao;

import cn.ibdsr.web.modular.shop.goods.transfer.GoodsQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品列表管理Dao
 *
 * @author XuZhipeng
 * @Date 2019-03-04 15:26:18
 */
public interface GoodsListDao {

    /**
     * 分页获取商品列表
     *
     * @param page
     * @param queryDTO 商品查询对象DTO
     *                 status 商品状态（1-销售中；2-已售罄；3-仓库中；）
     *                 goodsName 商品名称
     *                 minPrice 价格范围最低
     *                 maxPrice 价格范围最高
     *                 minSale 销量范围最低
     *                 maxSale 销量范围最高
     * @return
     */
    List<Map<String, Object>> list(@Param("page") Page page,
                                   @Param("queryDTO") GoodsQueryDTO queryDTO,
                                   @Param("orderByField") String orderByField,
                                   @Param("isAsc") Boolean isAsc);

    /**
     * 批量更新商品状态
     *
     * @param goodsIds 商品ID数组
     * @param statusCode 商品状态码（1-上架；2-下架；）
     * @param loginUserId 操作用户ID
     * @return
     */
    Integer batchUpdateGoodsStatus(@Param("goodsIds") Long[] goodsIds,
                                   @Param("statusCode") Integer statusCode,
                                   @Param("loginUserId") Long loginUserId);

    /**
     * 删除商品
     * @param goodsId 商品ID
     * @param loginUserId 操作用户
     * @return
     */
    Integer deleteGoods(@Param("goodsId")Long[] goodsId, @Param("loginUserId")Long loginUserId, @Param("isDelete")Integer isDelete);

    void deleteGoodsSku(@Param("goodsId")Long goodsId, @Param("loginUserId")Long loginUserId, @Param("isDelete")Integer isDelete);

}
