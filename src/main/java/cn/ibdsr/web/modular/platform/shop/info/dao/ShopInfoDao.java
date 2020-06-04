package cn.ibdsr.web.modular.platform.shop.info.dao;

import cn.ibdsr.web.common.persistence.model.ShopImgRel;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 店铺信息管理Dao
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:18
 */
public interface ShopInfoDao {

    /**
     * 分页获取车主商品订单退款列表
     *
     * @param page
     * @param condition 搜索关键字（店铺名称）
     * @param shopType 店铺类型（1-特产店铺；2-酒店店铺；）
     * @return
     */
    List<Map<String, Object>> list4Page(@Param("page") Page page,
                                        @Param("condition") String condition,
                                        @Param("shopType") Integer shopType,
                                        @Param("orderByField") String orderByField,
                                        @Param("isAsc") Boolean isAsc);

    /**
     * 批量新增店铺图片
     *
     * @param imgRelList 店铺图片关联集合
     * @return
     */
    Integer batchInsertShopImgRel(@Param("imgRelList") List<ShopImgRel> imgRelList);
}
