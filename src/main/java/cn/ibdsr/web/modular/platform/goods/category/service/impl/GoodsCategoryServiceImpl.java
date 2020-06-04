package cn.ibdsr.web.modular.platform.goods.category.service.impl;

import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.goods.CategoryLevel;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.GoodsCategoryMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.GoodsCategory;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.goods.category.dao.GoodsCategoryDao;
import cn.ibdsr.web.modular.platform.goods.category.service.IGoodsCategoryService;
import cn.ibdsr.web.modular.platform.goods.category.transfer.CategoryDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 商品类别管理Service
 *
 * @author XuZhipeng
 * @Date 2019-02-25 09:26:18
 */
@Service
public class GoodsCategoryServiceImpl implements IGoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 获取商品类别管理列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listGoodsCategorys() {
        return goodsCategoryDao.listGoodsCategorys();
    }

    /**
     * 以树形结构查询商品类别列表
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listOfTree() {
        return goodsCategoryDao.listOfTree();
    }

    /**
     * 获取上级所有节点
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> listParentNodes() {
        List<Map<String, Object>> mapList = goodsCategoryMapper.selectMaps(
                new EntityWrapper<GoodsCategory>()
                        .setSqlSelect("id, name")
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .eq("level", CategoryLevel.LEVEL1.getCode())
                        .orderBy("sequence", Boolean.TRUE));
        return mapList;
    }

    /**
     * 添加商品类别
     *
     * @param categoryDTO 类别信息
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    pid 父类别ID
     *                    iconImg 图标图片路径
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        checkCategoryName(categoryDTO, Boolean.TRUE);

        GoodsCategory goodsCategory = new GoodsCategory();

        goodsCategory.setName(categoryDTO.getName());
        goodsCategory.setFrontName(categoryDTO.getFrontName());
        goodsCategory.setPid(categoryDTO.getPid());
        // 大类默认父ID为0
        goodsCategory.setLevel(categoryDTO.getPid() == 0L ? CategoryLevel.LEVEL1.getCode() : CategoryLevel.LEVEL2.getCode());
        goodsCategory.setIconImg(ImageUtil.cutImageURL(categoryDTO.getIconImg()));

        // 查询序列最大值
        Integer maxSequence = goodsCategoryDao.getMaxSequence();
        maxSequence = maxSequence == null ? 0 : maxSequence;
        goodsCategory.setSequence(maxSequence + 1);
        goodsCategory.setCreatedTime(new Date());
        goodsCategory.setCreatedUser(ShiroKit.getUser().getId());
        goodsCategory.setIsDeleted(IsDeleted.NORMAL.getCode());
        goodsCategory.insert();

        // 增加维护上级所有ID，方便后期查询
        GoodsCategory updateCategory = new GoodsCategory();
        updateCategory.setIndexIds(getIndexIds(goodsCategory.getId()));
        updateCategory.setId(goodsCategory.getId());
        updateCategory.updateById();
    }

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
    @Override
    public void update(CategoryDTO categoryDTO) {
        // 校验类别ID，并获取类别信息
        GoodsCategory category = checkCategoryId(categoryDTO.getId());

        checkCategoryName(categoryDTO, Boolean.FALSE);

        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(categoryDTO.getId());
        goodsCategory.setName(categoryDTO.getName());
        goodsCategory.setFrontName(categoryDTO.getFrontName());

        // 修改类别为二级类别并且选择pid!=0，才更新上级ID
        if (category.getPid() != 0L && categoryDTO.getPid() != 0L) {
            goodsCategory.setPid(categoryDTO.getPid());
        }
        goodsCategory.setIconImg(ImageUtil.cutImageURL(categoryDTO.getIconImg()));
        goodsCategory.setModifiedTime(new Date());
        goodsCategory.setModifiedUser(ShiroKit.getUser().getId());
        goodsCategory.updateById();
    }

    /**
     * 根据类别ID查询商品类别
     *
     * @param id 类别ID
     * @return
     */
    @Override
    public GoodsCategory detailById(Long id) {
        return goodsCategoryMapper.selectById(id);
    }

    /**
     * 删除商品类别（逻辑删除）
     *
     * @param id 类别ID
     * @return
     */
    public void update2Del(Long id) {
        // 查询是否存在子类别
        List<GoodsCategory> categoryList = goodsCategoryMapper.selectList(
                new EntityWrapper<GoodsCategory>()
                        .eq("pid", id)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        boolean isExistChildren = (categoryList != null) && (categoryList.size() > 0);
        if (isExistChildren) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_HAVE_CHILDREN);
        }

        // 查询是否被商品绑定
        List<Goods> goodsList = goodsMapper.selectList(
                new EntityWrapper<Goods>()
                        .eq("category_id", id)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        boolean isExistGoods = (goodsList != null) && (goodsList.size() > 0);
        if (isExistGoods) {
            throw new BussinessException(BizExceptionEnum.CATEGORY_HAS_GOODS);
        }

        // 更新is_deleted为1
        GoodsCategory category = new GoodsCategory();
        category.setId(id);
        category.setIsDeleted(IsDeleted.DELETED.getCode());
        category.setModifiedTime(new Date());
        category.setModifiedUser(ShiroKit.getUser().getId());
        category.updateById();
    }

    /**
     * 上移
     *
     * @param id 类别ID
     * @return
     */
    @Override
    @Transactional
    public void moveUp(Long id) {
        // 校验类别ID，并获取类别信息
        GoodsCategory category = checkCategoryId(id);

        // 查询上一位类别
        GoodsCategory exchangeCategory = goodsCategoryDao.getByUp(category.getPid(), category.getSequence());
        if (exchangeCategory == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_UP_ERROR);
        }
        // 交换序列值
        exchangeSequence(category, exchangeCategory);
    }

    /**
     * 下移
     *
     * @param id 类别ID
     * @return
     */
    @Override
    @Transactional
    public void moveDown(Long id) {
        // 校验类别ID，并获取类别信息
        GoodsCategory category = checkCategoryId(id);

        // 查询下一位类别
        GoodsCategory exchangeCategory = goodsCategoryDao.getByDown(category.getPid(), category.getSequence());
        if (exchangeCategory == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_DOWN_ERROR);
        }
        // 交换序列值
        exchangeSequence(category, exchangeCategory);
    }

    /**
     * 根据类别Id查询类别信息
     *
     * @param id 类别ID
     * @return
     */
    @Override
    public Map<String, Object> getCategoryById(Long id) {
        // 校验类别ID，并获取类别信息
        GoodsCategory category = checkCategoryId(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", category.getId());
        result.put("name", category.getName());
        result.put("frontName", category.getFrontName());
        result.put("pid", category.getPid());
        result.put("iconImg", ImageUtil.setImageURL(category.getIconImg()));
        return result;
    }

    /**
     * 校验类别ID，并获取类别信息
     *
     * @param id 类别ID
     * @return
     */
    private GoodsCategory checkCategoryId(Long id) {
        if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_ID_IS_NULL);
        }
        GoodsCategory category = goodsCategoryMapper.selectById(id);
        if (category == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_IS_NOT_EXIST);
        }
        return category;
    }

    /**
     * 校验类别名称
     *
     * @param categoryDTO 类别信息
     *                    name 类别名称
     *                    frontName 前台展示名称
     *                    id 类别ID（修改时必传）
     * @param isInsert 是否新增
     */
    private void checkCategoryName(CategoryDTO categoryDTO, Boolean isInsert) {

        // 校验类别名称是否存在
        Wrapper<GoodsCategory> nameWrapper = new EntityWrapper()
                .eq("name", categoryDTO.getName())
                .eq("is_deleted", IsDeleted.NORMAL.getCode());
        if (!isInsert) {
            // 修改时，排除当前对象
            nameWrapper.ne("id", categoryDTO.getId());
        }
        List<GoodsCategory> categoryList = goodsCategoryMapper.selectList(nameWrapper);
        if (categoryList != null && categoryList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_NAME_IS_EXIST);
        }

        // 校验类别前台名称是否存在
        Wrapper<GoodsCategory> frontNameWrapper = new EntityWrapper()
                .eq("front_name", categoryDTO.getFrontName())
                .eq("is_deleted", IsDeleted.NORMAL.getCode());
        if (!isInsert) {
            // 修改时，排除当前对象
            frontNameWrapper.ne("id", categoryDTO.getId());
        }
        categoryList = goodsCategoryMapper.selectList(frontNameWrapper);
        if (categoryList != null && categoryList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_FRONT_NAME_IS_EXIST);
        }
    }

    /**
     * 获取所有上级类别ID
     *
     * @param categoryId 类别ID
     * @return
     */
    private String getIndexIds(Long categoryId) {
        StringBuilder sb = new StringBuilder();
        List<GoodsCategory> categoryList = listParentCategorys(categoryId, new ArrayList<GoodsCategory>());
        if (categoryList == null || categoryList.size() == 0) {
            return sb.toString();
        }

        int i = categoryList.size();
        for (i = i - 1; i >= 0; i--) {
            GoodsCategory category = categoryList.get(i);
            sb.append(category.getId()).append(",");
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }

    /**
     * 获取所有类别
     *
     * @param categoryId 类别ID
     * @return
     */
    private List<GoodsCategory> listParentCategorys(Long categoryId, List<GoodsCategory> resultList) {
        GoodsCategory category = goodsCategoryMapper.selectById(categoryId);
        if (category == null) {
            return resultList;
        }
        resultList.add(category);
        if (category.getPid() == 0) {
            return resultList;
        }
        return listParentCategorys(category.getPid(), resultList);
    }

    /**
     * 交换序列值
     *
     * @param targetCategory 当前操作的目标对象
     * @param exchangeCategory 被交换的对象
     * @return
     */
    private void exchangeSequence(GoodsCategory targetCategory, GoodsCategory exchangeCategory) {
        Integer seqTemp = exchangeCategory.getSequence();

        exchangeCategory.setSequence(targetCategory.getSequence());

        Date nowDate = new Date();
        Long loginUserId = ShiroKit.getUser().getId();

        // 更新被交换对象
        exchangeCategory.setModifiedTime(nowDate);
        exchangeCategory.setModifiedUser(loginUserId);
        exchangeCategory.updateById();

        // 更新当前目标对象
        targetCategory.setSequence(seqTemp);
        targetCategory.setModifiedTime(nowDate);
        targetCategory.setModifiedUser(loginUserId);
        targetCategory.updateById();
    }
}
