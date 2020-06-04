package cn.ibdsr.web.modular.shop.goods.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.GoodsCategoryMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsImgMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsSkuMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.GoodsCategory;
import cn.ibdsr.web.common.persistence.model.GoodsImg;
import cn.ibdsr.web.common.persistence.model.GoodsIntro;
import cn.ibdsr.web.common.persistence.model.GoodsSku;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsDao;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsImgDao;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsIntroDao;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsSkuDao;
import cn.ibdsr.web.modular.shop.goods.service.GoodsService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsImgDTO;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
import cn.ibdsr.web.modular.shop.goods.warpper.GoodsImgWarpper;
import cn.ibdsr.web.modular.shop.goods.warpper.GoodsSkuWarpper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    private GoodsImgMapper goodsImgMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private GoodsImgDao goodsImgDao;

    @Autowired
    private GoodsIntroDao goodsIntroDao;

    @Autowired
    private GoodsSkuDao goodsSkuDao;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private ShopUserMapper shopUserMapper;

    private static final int GOODS_IMAGE_MAX = 10;

    private static final int GOODS_SPECS_MAX = 3;

    private static final int GOODS_SUB_SPECS_MAX = 10;

    @Override
    public SuccessDataTip goodsCategoryList(int pid) {
        Wrapper wrapper = new EntityWrapper();
        wrapper.setSqlSelect("id", "name");
        wrapper.eq("is_deleted", IsDeleted.NORMAL.getCode());
        wrapper.eq("pid", pid);
        wrapper.orderBy("sequence");
        List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.selectList(wrapper);
        return new SuccessDataTip(JSONObject.parseArray(JSONObject.toJSONString(goodsCategoryList)));
    }

    @Transactional
    @Override
    public SuccessTip insert(Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content) {
        //校验
        if (StringUtils.isBlank(goods.getName())) {
            throw new BussinessException(BizExceptionEnum.GOODS_NAME_NOT_BLANK);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        List<Goods> goodsList = goodsMapper.selectList(new EntityWrapper<Goods>().eq("shop_id", shopUser.getShopId()).eq("name", goods.getName()));
        if (goodsList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_NAME_REPEAT);
        }
        if (goods.getCategoryId() == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_NOT_BLANK);
        }
        GoodsCategory goodsCategory = goodsCategoryMapper.selectById(goods.getCategoryId());
        if (goodsCategory == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_NOT_EXIST);
        }
        if (goodsCategory.getLevel() != 2) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_SECOND);
        }
        if (StringUtils.isBlank(imageListJsonString)) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MIN);
        }
        List<GoodsImg> goodsImgList = JSONObject.parseArray(imageListJsonString, GoodsImg.class);
        if (goodsImgList.size() == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MIN);
        }
        if (goodsImgList.size() > GOODS_IMAGE_MAX) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MAX);
        }
        if (StringUtils.isNotBlank(goods.getSpecsList())) {
            JSONObject jsonObject = JSONObject.parseObject(goods.getSpecsList());
            if (jsonObject.keySet().size() > GOODS_SPECS_MAX) {
                throw new BussinessException(BizExceptionEnum.GOODS_SPECS_MAX);
            }
            for (String key : jsonObject.keySet()) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                if (jsonArray.size() > GOODS_SUB_SPECS_MAX) {
                    throw new BussinessException(BizExceptionEnum.GOODS_SUB_SPECS_MAX);
                }
            }
        }
        if (goods.getPrice() == null || goods.getPrice().compareTo(new BigDecimal(0)) < 0 || goods.getPrice().compareTo(new BigDecimal(10000000)) > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_PRICE_MIN_MAX);
        }
        if (goods.getReferPrice() != null) {
            if (goods.getReferPrice().compareTo(new BigDecimal(0)) < 0 || goods.getReferPrice().compareTo(new BigDecimal(10000000)) > 0) {
                throw new BussinessException(BizExceptionEnum.GOODS_REFER_PRICE_MIN_MAX);
            }
        }
        if (goods.getStock() == null || goods.getStock() < 0 || goods.getStock() > 100000) {
            throw new BussinessException(BizExceptionEnum.GOODS_STOCK_MIN_MAX);
        }
        //插入操作
        Date now = new Date();
        goods.setShopId(((ShopData) shiroUser.getData()).getShopId());
        // 商品主图片
        goods.setMainImg(ImageUtil.cutImageURL(goodsImgList.get(0).getImg()));

        goods.setCreatedUser(shiroUser.getId());
        goods.setCreatedTime(now);
        goods.setPrice(goods.getPrice().multiply(new BigDecimal(100)));
        if (goods.getReferPrice() != null) {
            goods.setReferPrice(goods.getReferPrice().multiply(new BigDecimal(100)));
        }
        goods.setExpressFee(goods.getExpressFee().multiply(new BigDecimal(100)));
        if (StringUtils.isNotBlank(goods.getSpecsList())) {
            goods.setSpecsList(JSONObject.parseObject(goods.getSpecsList()).toJSONString());
        }
        goods.insert();
        for (GoodsImg goodsImg : goodsImgList) {
            goodsImg.setGoodsId(goods.getId());
            goodsImg.setImg(ImageUtil.cutImageURL(goodsImg.getImg()));
            goodsImg.setCreatedUser(shiroUser.getId());
            goodsImg.setCreatedTime(now);
            goodsImg.insert();
        }
        List<JSONObject> goodsSkuList = JSONObject.parseArray(goodsSkuListJsonString, JSONObject.class);
        for (JSONObject jsonObject : goodsSkuList) {
            if (jsonObject.get("price") == null || jsonObject.getBigDecimal("price").compareTo(new BigDecimal(0)) < 0 || jsonObject.getBigDecimal("price").compareTo(new BigDecimal(10000000)) > 0) {
                throw new BussinessException(BizExceptionEnum.GOODS_SKU_PRICE_MIN_MAX);
            }
            if (jsonObject.get("referPrice") != null) {
                if (jsonObject.getBigDecimal("referPrice").compareTo(new BigDecimal(0)) < 0 || jsonObject.getBigDecimal("referPrice").compareTo(new BigDecimal(10000000)) > 0) {
                    throw new BussinessException(BizExceptionEnum.GOODS_SKU_REFER_PRICE_MIN_MAX);
                }
            }
            if (goods.getStock() == null || goods.getStock() < 0 || goods.getStock() > 100000) {
                throw new BussinessException(BizExceptionEnum.GOODS_SKU_STOCK_MIN_MAX);
            }
            GoodsSku goodsSku = new GoodsSku();
            goodsSku.setGoodsId(goods.getId());
            goodsSku.setSpecs(jsonObject.getString("specs"));
            goodsSku.setPrice(jsonObject.getBigDecimal("price").multiply(new BigDecimal(100)));
            if (jsonObject.get("referPrice") != null) {
                goodsSku.setReferPrice(jsonObject.getBigDecimal("referPrice").multiply(new BigDecimal(100)));
            }
            goodsSku.setStock(jsonObject.getIntValue("stock"));
            if (StringUtils.isNotBlank(jsonObject.getString("img"))) {
                goodsSku.setImg(ImageUtil.cutImageURL(jsonObject.getString("img")));
            } else {
                goodsSku.setImg(ImageUtil.cutImageURL(goodsImgList.get(0).getImg()));
            }
            goodsSku.setCreatedUser(shiroUser.getId());
            goodsSku.setCreatedTime(now);
            goodsSku.insert();
        }
        if (StringUtils.isNotBlank(content)) {
            GoodsIntro goodsIntro = new GoodsIntro();
            goodsIntro.setGoodsId(goods.getId());
            goodsIntro.setIntroContent(content);
            goodsIntro.setCreatedUser(shiroUser.getId());
            goodsIntro.setCreatedTime(now);
            goodsIntro.insert();
        }
        return new SuccessTip();
    }

    /**
     * @param goodsId                商品ID
     * @param goods                  商品对象
     * @param imageListJsonString    商品图片Json字符串
     * @param goodsSkuListJsonString 商品SKU的Json字符串
     * @param content                商品详情描述
     * @return
     * @Description 方法描述
     */
    @Override
    @Transactional
    public SuccessTip update(Long goodsId, Goods goods, String imageListJsonString, String goodsSkuListJsonString, String content) {
        //判断是否存在该商品
        if (ToolUtil.isOneEmpty(goodsId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        List<Goods> goodsList = goodsMapper.selectList(
                new EntityWrapper<Goods>()
                        .eq("shop_id", shopUser.getShopId())
                        .eq("name", goods.getName())
                        .ne("id", goodsId));
        //同一店铺内商品名唯一
        if (goodsList.size() > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_NAME_REPEAT);
        }
        //商品分类不能为空
        if (goods.getCategoryId() == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_NOT_BLANK);
        }
        GoodsCategory goodsCategory = goodsCategoryMapper.selectById(goods.getCategoryId());
        //判断分类是否存在
        if (goodsCategory == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_NOT_EXIST);
        }
        //二级分类不能为空
        if (goodsCategory.getLevel() != 2) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_SECOND);
        }
        //商品图片不能为空
        if (StringUtils.isBlank(imageListJsonString)) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MIN);
        }
        List<GoodsImgDTO> goodsImgList = JSONObject.parseArray(imageListJsonString, GoodsImgDTO.class);
        //商品图片不能为空
        if (goodsImgList.size() == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MIN);
        }
        //商品图片允许最多上传10张
        if (goodsImgList.size() > GOODS_IMAGE_MAX) {
            throw new BussinessException(BizExceptionEnum.GOODS_IMAGE_MAX);
        }

        goods.setId(goodsId);
        //商品规格设置
        if (StringUtils.isNotBlank(goods.getSpecsList())) {
            JSONObject jsonObject = JSONObject.parseObject(goods.getSpecsList());
            //商品规格最多3项
            if (jsonObject.keySet().size() > GOODS_SPECS_MAX) {
                throw new BussinessException(BizExceptionEnum.GOODS_SPECS_MAX);
            }
            for (String key : jsonObject.keySet()) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                //商品规格子项最多10项
                if (jsonArray.size() > GOODS_SUB_SPECS_MAX) {
                    throw new BussinessException(BizExceptionEnum.GOODS_SUB_SPECS_MAX);
                }
            }
        }

        //商品价格设置
        //商品价格不能为空，且范围为0-100000元
        if (goods.getPrice() == null || goods.getPrice().compareTo(new BigDecimal(0)) < 0 || goods.getPrice().compareTo(new BigDecimal(10000000)) > 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_PRICE_MIN_MAX);
        }
        //商品划线价可以为空，且范围为0-100000元
        if (goods.getReferPrice() != null || ToolUtil.isNotEmpty(goods.getReferPrice())) {
            if (goods.getReferPrice().compareTo(new BigDecimal(0)) < 0 || goods.getReferPrice().compareTo(new BigDecimal(10000000)) > 0) {
                throw new BussinessException(BizExceptionEnum.GOODS_REFER_PRICE_MIN_MAX);
            }
        }
        //商品库存不能为空，且范围为0-100000
        if (goods.getStock() == null || goods.getStock() < 0 || goods.getStock() > 100000) {
            throw new BussinessException(BizExceptionEnum.GOODS_STOCK_MIN_MAX);
        }

        //更新基本信息
        Date now = new Date();
        goods.setPrice(goods.getPrice().multiply(new BigDecimal(100)));
        if (goods.getReferPrice() != null) {
            goods.setReferPrice(goods.getReferPrice().multiply(new BigDecimal(100)));
        }
        goods.setExpressFee(goods.getExpressFee().multiply(new BigDecimal(100)));

        // 商品主图片
        goods.setMainImg(ImageUtil.cutImageURL(goodsImgList.get(0).getImg()));

        goods.setModifiedUser(shiroUser.getId());
        goods.setModifiedTime(now);
        goods.updateById();

        //更新图片信息
        goodsImgMapper.delete(new EntityWrapper<GoodsImg>().eq("goods_id", goodsId));
        for (GoodsImgDTO goodsImgDTO : goodsImgList) {
            GoodsImg goodsImg = new GoodsImg();
            goodsImg.setGoodsId(goodsId);
            goodsImg.setImg(ImageUtil.cutImageURL(goodsImgDTO.getImg()));
            goodsImg.setCreatedUser(shiroUser.getId());
            goodsImg.setCreatedTime(now);
            goodsImg.setSequence(goodsImgDTO.getSequence());
            goodsImg.insert();
        }

        //更新商品的sku
        List<JSONObject> goodsSkuList = JSONObject.parseArray(goodsSkuListJsonString, JSONObject.class);
        deleteGoodsSku(goodsId, goodsSkuList);
        for (JSONObject jsonObject : goodsSkuList) {
            //查找是否存在该sku
            List<GoodsSku> goodsSkuExist = goodsSkuMapper.selectList(
                    new EntityWrapper<GoodsSku>()
                            .eq("goods_id", goodsId)
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .eq("specs", JSONObject.toJSONString(jsonObject.getJSONObject("specs")))
            );
            if (goodsSkuExist == null || 0 == goodsSkuExist.size()) { //如果不存在，则插入新的sku
                GoodsSku goodsSku = new GoodsSku();
                goodsSku.setGoodsId(goods.getId());
                goodsSku.setSpecs(JSONObject.toJSONString(jsonObject.getJSONObject("specs")));
                goodsSku.setPrice(jsonObject.getBigDecimal("price").multiply(new BigDecimal(100)));
                if (jsonObject.getBigDecimal("referPrice") != null || ToolUtil.isNotEmpty(jsonObject.getBigDecimal("referPrice"))) {
                    goodsSku.setReferPrice(jsonObject.getBigDecimal("referPrice").multiply(new BigDecimal(100)));
                } else {
                    goodsSku.setReferPrice(jsonObject.getBigDecimal("referPrice"));
                }
                goodsSku.setStock(jsonObject.getIntValue("stock"));
                if (StringUtils.isNotBlank(jsonObject.getString("img"))) {
                    goodsSku.setImg(ImageUtil.cutImageURL(jsonObject.getString("img")));

                }
                goodsSku.setCreatedUser(shiroUser.getId());
                goodsSku.setCreatedTime(now);
                goodsSku.insert();
            } else { // 如果存在，直接修改
                GoodsSku goodsSku = goodsSkuExist.get(0);
                goodsSku.setSpecs(JSONObject.toJSONString(jsonObject.getJSONObject("specs")));
                goodsSku.setPrice(jsonObject.getBigDecimal("price").multiply(new BigDecimal(100)));
                if (jsonObject.getBigDecimal("referPrice") != null || ToolUtil.isNotEmpty(jsonObject.getBigDecimal("referPrice"))) {
                    goodsSku.setReferPrice(jsonObject.getBigDecimal("referPrice").multiply(new BigDecimal(100)));
                } else {
                    goodsSku.setReferPrice(jsonObject.getBigDecimal("referPrice"));
                }
                goodsSku.setStock(jsonObject.getIntValue("stock"));
                if (StringUtils.isNotBlank(jsonObject.getString("img"))) {
                    goodsSku.setImg(ImageUtil.cutImageURL(jsonObject.getString("img")));

                }
                goodsSku.setModifiedTime(now);
                goodsSku.setModifiedUser(shiroUser.getId());
                goodsSku.updateById();
            }

        }

        //更新商品的描述信息
        if (StringUtils.isNotBlank(content)) {
            goodsIntroDao.updateGoodsIntro(content, goodsDao.selectGoodsIntroId(goodsId));
        }

        return new SuccessTip();
    }

    /**
     * @param goodsId           商品id
     * @param goodsSkuListInput 输入的shu集合
     * @return
     * @Description 删除sku
     */
    private void deleteGoodsSku(Long goodsId, List<JSONObject> goodsSkuListInput) {
        List<GoodsSku> goodsSkuExist = goodsSkuMapper.selectList(
                new EntityWrapper<GoodsSku>()
                        .eq("goods_id", goodsId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
        );
        for (GoodsSku goodsSku : goodsSkuExist) {
            List<String> specList = new ArrayList<>();
            for (JSONObject jsonObject : goodsSkuListInput) {
                specList.add(JSONObject.toJSONString(jsonObject.getJSONObject("specs")));
            }
            if (specList.contains(goodsSku.getSpecs())) {
                continue;
            } else {
                goodsSku.deleteById();
            }
        }
    }

    /**
     * @param goodsId 商品id
     * @return
     * @Description 根据goodsID查找商品基本属性
     */
    @Override
    public GoodsVO getGoods(Long goodsId) {
        //判断是否存在该商品
        if (ToolUtil.isOneEmpty(goodsId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //商品的基本属性信息
        GoodsVO goodsProp = goodsDao.getGoods(goodsId);
        if (ToolUtil.isEmpty(goodsProp)) {
            throw new BussinessException(BizExceptionEnum.GOODS_IS_NOT_EXIST);
        }
        goodsProp.setPrice(new BigDecimal(AmountFormatUtil.amountFormat(goodsProp.getPrice())));
        if (goodsProp.getReferPrice() != null) {
            goodsProp.setReferPrice(new BigDecimal(AmountFormatUtil.amountFormat(goodsProp.getReferPrice())));
        }
        goodsProp.setExpressFee(new BigDecimal(AmountFormatUtil.amountFormat(goodsProp.getExpressFee())));


        //商品的一二级类目信息
        GoodsCategory goodsCategory = goodsCategoryMapper.selectById(goodsProp.getCategoryId());
        if (goodsCategory.getLevel() != 2) {
            throw new BussinessException(BizExceptionEnum.GOODS_CATEGORY_SECOND_IS_NULL);
        } else {
            List<Long> categoryInfo = new ArrayList<>();
            categoryInfo.add(goodsCategory.getPid());
            categoryInfo.add(goodsProp.getCategoryId());
            goodsProp.setCategoryInfo(categoryInfo);
        }

        //获取商品的图片集
        JSONArray imgJson = (JSONArray) JSON.toJSON(getGoodsImgByGoodsId(goodsId));
        goodsProp.setImageList(imgJson);

        //获取商品的SKU集
        JSONArray goodsSkuJson = (JSONArray) JSON.toJSON(getGoodsSkuByGoodsId(goodsId));
        goodsProp.setGoodsSkuList(goodsSkuJson);

        //获取商品详情描述
        goodsProp.setIntroContent(goodsIntroDao.getIntroContentByGoodsId(goodsId));

        return goodsProp;
    }

    /**
     * @param goodsId 商品id
     * @return
     * @Description 根据goodsID查找商品的图片集
     */
    private List<GoodsImg> getGoodsImgByGoodsId(Long goodsId) {
        Wrapper<GoodsImg> entityWrapper = new EntityWrapper<>();
        List<Map<String, Object>> goodsImgs = goodsImgMapper.selectMaps(
                entityWrapper.eq("goods_id", goodsId)
                        .eq("is_deleted", Boolean.FALSE));

        return (List<GoodsImg>) new GoodsImgWarpper(goodsImgs).warp();
    }

    /**
     * @param goodsId 商品id
     * @return
     * @Description 根据goodsID查找商品的sku集
     */
    private List<GoodsSku> getGoodsSkuByGoodsId(Long goodsId) {
        Wrapper<GoodsSku> entityWrapper = new EntityWrapper<>();
        List<Map<String, Object>> goodsSku = goodsSkuMapper.selectMaps(
                entityWrapper.eq("goods_id", goodsId)
                        .eq("is_deleted", Boolean.FALSE));

        return (List<GoodsSku>) new GoodsSkuWarpper(goodsSku).warp();
    }


}
