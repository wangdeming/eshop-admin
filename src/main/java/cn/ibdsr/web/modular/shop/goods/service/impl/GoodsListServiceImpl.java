package cn.ibdsr.web.modular.shop.goods.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.shop.PlatformManageStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopApplyStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsSkuMapper;
import cn.ibdsr.web.common.persistence.dao.PlatformManageMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.GoodsSku;
import cn.ibdsr.web.common.persistence.model.PlatformManage;
import cn.ibdsr.web.modular.shop.goods.dao.GoodsListDao;
import cn.ibdsr.web.modular.shop.goods.service.IGoodsListService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsQueryDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 商品列表管理Service
 *
 * @author XuZhipeng
 * @Date 2019-03-04 15:26:18
 */
@Service
public class GoodsListServiceImpl implements IGoodsListService {

    @Autowired
    private GoodsListDao goodsListDao;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Autowired
    PlatformManageMapper platformManageMapper;

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
    @Override
    public List<Map<String, Object>> list(Page page, GoodsQueryDTO goodsQueryDTO) {
        StaticCheck.check(goodsQueryDTO);

        // 登录用户所属店铺ID
        ShiroUser shiroUser = ShiroKit.getUser();
        goodsQueryDTO.setShopId(((ShopData) shiroUser.getData()).getShopId());

        // 价格 元 => 分
        if (null != goodsQueryDTO.getMinPrice()) {
            goodsQueryDTO.setMinPrice(goodsQueryDTO.getMinPrice().multiply(BigDecimal.valueOf(100)));
        }
        if (null != goodsQueryDTO.getMaxPrice()) {
            goodsQueryDTO.setMaxPrice(goodsQueryDTO.getMaxPrice().multiply(BigDecimal.valueOf(100)));
        }

        List<Map<String, Object>> goodsList = goodsListDao.list(page, goodsQueryDTO, page.getOrderByField(), page.isAsc());

        for (Map<String, Object> goods: goodsList) {
            if ((Integer)goods.get("platformManage") == 0) {
                PlatformManage platformManage = getPlatformInfos((Long)goods.get("id"));
                if (platformManage != null) {
                    goods.put("platformManageReason",platformManage.getPlatformManageReason()); //平台端下架理由
                    goods.put("shopApply", platformManage.getShopApply()); //商家是否申请上架，0未申请，1已申请
                }
            }
        }
        return goodsList;
    }

    /**
     * 获得商品的平台审核信息
     * @param goodsId 商品id
     * @return
     */
    private PlatformManage getPlatformInfos(Long goodsId) {
        List<PlatformManage> platformManageList = platformManageMapper.selectList(
                new EntityWrapper<PlatformManage>()
                        .eq("goods_id", goodsId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode())
                        .orderBy("created_time",false)
                        .last("LIMIT 1"));
        if (platformManageList == null || 0 == platformManageList.size()){
            return null;
        }
        else {
            return platformManageList.get(0);
        }
    }

    /**
     * 批量更新商品状态
     *
     * @param goodsIds 商品ID数组
     * @param statusCode 商品状态（1-上架；2-下架；）
     */
    @Override
    public void batchUpdateGoodsStatus(Long[] goodsIds, Integer statusCode) {
        if (goodsIds == null || goodsIds.length == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_ID_IS_NULL);
        }

        List<Goods> dataList = goodsMapper.selectList(
                new EntityWrapper()
                        .in("id", goodsIds)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.GOODS_IS_NOT_EXIST);
        }
        goodsListDao.batchUpdateGoodsStatus(goodsIds, statusCode, ShiroKit.getUser().getId());
    }

    /**
     * 更新商品序列号
     *
     * @param goodsId 商品ID
     * @param sequence 序列号
     */
    @Override
    public void updateSequence(Long goodsId, Integer sequence) {
        if (ToolUtil.isEmpty(goodsId)) {
            throw new BussinessException(BizExceptionEnum.GOODS_ID_IS_NULL);
        }
        if (ToolUtil.isEmpty(sequence)) {
            throw new BussinessException(BizExceptionEnum.GOODS_SEQUENCE_IS_NULL);
        }

        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new BussinessException(BizExceptionEnum.GOODS_IS_NOT_EXIST);
        }

        goods.setSequence(sequence);
        goods.setModifiedTime(new Date());
        goods.setModifiedUser(ShiroKit.getUser().getId());
        goods.updateById();
    }
    /**
     * 删除商品
     *
     * @param goodsId 商品
     * @return
     */
    @Override
    public void deleteGoods(Long[] goodsId){
        if (goodsId == null || goodsId.length == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_ID_IS_NULL);
        }

        List<Goods> dataList = goodsMapper.selectList(
                new EntityWrapper()
                        .in("id", goodsId)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.GOODS_IS_NOT_EXIST);
        }
        goodsListDao.deleteGoods(goodsId, ShiroKit.getUser().getId(), IsDeleted.DELETED.getCode());
        for(Long id:goodsId){
            List<GoodsSku> goodsSkuList = goodsSkuMapper.selectList(
                    new EntityWrapper()
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .eq("goods_id", id)
            );
            Boolean Exist = (goodsSkuList != null) && (goodsSkuList.size() > 0);
            if (Exist) {
                goodsListDao.deleteGoodsSku(id, ShiroKit.getUser().getId(), IsDeleted.DELETED.getCode());
            }
        }
    }

    /**
     * 被系统下架的商品批量申请重新上架
     *
     * @param goodsIds 商品ID数组
     */
    @Override
    public void applyGoodsOnShelf(Long[] goodsIds) {
        if (goodsIds == null || goodsIds.length == 0) {
            throw new BussinessException(BizExceptionEnum.GOODS_ID_IS_NULL);
        }

        List<Goods> dataList = goodsMapper.selectList(
                new EntityWrapper()
                        .in("id", goodsIds)
                        .eq("is_deleted", IsDeleted.NORMAL.getCode()));
        Boolean isExist = (dataList != null) && (dataList.size() > 0);
        if (!isExist) {
            throw new BussinessException(BizExceptionEnum.GOODS_IS_NOT_EXIST);
        }
        else {
            for (Goods goods: dataList) {
                //更新平台审核表信息
                List<PlatformManage> platformManageList = platformManageMapper.selectList(
                        new EntityWrapper<PlatformManage>()
                                .eq("goods_id", goods.getId())
                                .eq("is_deleted", IsDeleted.NORMAL.getCode())
                                .orderBy("created_time", false)
                                .last("LIMIT 1")
                );
                if (platformManageList != null && platformManageList.size() > 0) {
                    PlatformManage platformManage = platformManageList.get(0);
                    platformManage.setShopApply(ShopApplyStatus.YES.getCode());
                    platformManage.updateById();
                }
            }
        }
    }
}
