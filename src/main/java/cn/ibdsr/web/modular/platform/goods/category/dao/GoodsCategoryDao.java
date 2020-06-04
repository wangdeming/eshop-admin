package cn.ibdsr.web.modular.platform.goods.category.dao;

import cn.ibdsr.web.common.persistence.model.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品类别管理Dao
 *
 * @author XuZhipeng
 * @Date 2019-02-25 09:26:18
 */
public interface GoodsCategoryDao {

    /**
     * 获取商品类别列表
     *
     * @return
     */
    List<Map<String, Object>> listGoodsCategorys();

    /**
     * 以树形结构查询商品类别列表
     *
     * @return
     */
    List<Map<String, Object>> listOfTree();

    /**
     * 查询序列最大值
     *
     @return
     */
    Integer getMaxSequence();

    /**
     * 查询上一位类别
     *
     * @param pid 父ID
     * @param sequence 序列值
     * @return
     */
    GoodsCategory getByUp(@Param("pid")Long pid,
                          @Param("sequence")Integer sequence);

    /**
     * 查询下一位类别
     *
     * @param pid 父ID
     * @param sequence 序列值
     * @return
     */
    GoodsCategory getByDown(@Param("pid")Long pid,
                            @Param("sequence")Integer sequence);
}
