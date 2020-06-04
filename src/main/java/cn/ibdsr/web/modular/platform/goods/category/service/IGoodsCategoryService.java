package cn.ibdsr.web.modular.platform.goods.category.service;

import cn.ibdsr.web.common.persistence.model.GoodsCategory;
import cn.ibdsr.web.modular.platform.goods.category.transfer.CategoryDTO;

import java.util.List;
import java.util.Map;

/**
 * 商品类别管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-25 09:26:18
 */
public interface IGoodsCategoryService {

    /**
     * 获取商品类别管理列表
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
     * 获取上级所有节点
     */
    List<Map<String,Object>> listParentNodes();

    /**
     * 添加商品类别
     *
     * @param categoryDTO 类别信息
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    pid 父类别ID
     *                    iconImg 图标图片路径
     */
    void add(CategoryDTO categoryDTO);

    /**
     * 修改商品类别
     *
     * @param categoryDTO 类别信息
     *                    id 类别ID
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    pid 父类别ID
     *                    iconImg 图标图片路径
     * @return
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 根据类别ID查询商品类别
     *
     * @param id 类别ID
     * @return
     */
    GoodsCategory detailById(Long id);

    /**
     * 删除商品类别（逻辑删除）
     *
     * @param id 类别ID
     * @return
     */
    void update2Del(Long id);

    /**
     * 上移
     *
     * @param id 类别ID
     * @return
     */
    void moveUp(Long id);

    /**
     * 下移
     *
     * @param id 类别ID
     * @return
     */
    void moveDown(Long id);

    /**
     * 根据类别Id查询类别信息
     *
     * @param id 类别ID
     * @return
     */
    Map<String, Object> getCategoryById(Long id);
}
