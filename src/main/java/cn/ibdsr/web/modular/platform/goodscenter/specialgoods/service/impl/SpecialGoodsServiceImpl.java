package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.service.impl;

import cn.ibdsr.core.check.StaticCheck;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.shop.PlatformManageStatus;
import cn.ibdsr.web.common.constant.state.shop.ShopApplyStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.GoodsCategoryMapper;
import cn.ibdsr.web.common.persistence.dao.GoodsMapper;
import cn.ibdsr.web.common.persistence.dao.PlatformManageMapper;
import cn.ibdsr.web.common.persistence.model.Goods;
import cn.ibdsr.web.common.persistence.model.PlatformManage;
import cn.ibdsr.web.core.util.AmountFormatUtil;
import cn.ibdsr.web.core.util.ImageUtil;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.dao.SpecialGoodsDao;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsListVO;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsQueryDTO;
import cn.ibdsr.web.modular.shop.goods.service.GoodsService;
import cn.ibdsr.web.modular.shop.goods.transfer.GoodsVO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.service.SpecialGoodsService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-特产商品Service
 * @Version V1.0
 * @CreateDate 2019/5/23 16:46
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui           类说明
 */
@Service
public class SpecialGoodsServiceImpl implements SpecialGoodsService {

    @Autowired
    private SpecialGoodsDao specialGoodsDao;

    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;

    @Autowired
    PlatformManageMapper platformManageMapper;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 分页获取商品列表
     *
     * @param page
     * @param queryDTO 商品查询对象DTO
     *                 status 商品状态（1-销售中；2-已售罄；3-仓库中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 goodsName 商品名称
     *                 goodsId 商品ID
     *                 shopName 店铺名称
     *                 shopId 店铺ID
     *                 firstCategoryId 商品一级类目Id
     *                 secondCategoryId 商品二级类目Id
     * @return
     */
    @Override
    public List<Map<String, Object>> goodsList(Page page, SpecialGoodsQueryDTO queryDTO) {

        StaticCheck.check(queryDTO);

        List<Map<String, Object>> specialGoodsList = specialGoodsDao.goodsList(page, queryDTO, page.getOrderByField(), page.isAsc());
        for (Map<String, Object> goods: specialGoodsList) {
            Long secondCategoryId = (Long) goods.get("secondCategoryId");
            Long firstCategoryId = (Long) goods.get("fistCategoryId");
            goods.put("secondCategory", goodsCategoryMapper.selectById(secondCategoryId).getName());
            if (firstCategoryId != 0 && ToolUtil.isNotEmpty(firstCategoryId)) {
                goods.put("firstCategory", goodsCategoryMapper.selectById(firstCategoryId).getName());
            }
            if ((Integer)goods.get("platformManage") == 0) {
                PlatformManage platformManage = getPlatformInfos((Long)goods.get("id"));
                if (platformManage != null) {
                    goods.put("platformManageReason",platformManage.getPlatformManageReason()); //平台端下架理由
                    goods.put("shopApply", platformManage.getShopApply()); //商家是否申请上架，0未申请，1已申请
                }
            }
        }

        return specialGoodsList;
    }

    /**
     * 系统下架商品
     *
     * @param goodsId 商品ID
     **@param reason 下架原因
     * @return
     */
    @Override
    public void offShelf(Long goodsId, String reason) {
        //插入数据
        GoodsVO goodsDetail = goodsService.getGoods(goodsId);
        PlatformManage platformManage = new PlatformManage();
        platformManage.setCreatedUser(ShiroKit.getUser().getId());
        platformManage.setCreatedTime(new Date());
        platformManage.setGoodsId(goodsId);
        platformManage.setShopId(goodsDetail.getShopId());
        platformManage.setPlatformManage(PlatformManageStatus.OFFSHELF.getCode());
        platformManage.setPlatformManageReason(reason);
        platformManage.insert();

        //更新商品字段信息
        Goods goods = goodsMapper.selectById(goodsId);
        if (ToolUtil.isNotEmpty(goods)) {
            goods.setPlatformManage(PlatformManageStatus.OFFSHELF.getCode());
            goods.updateById();
        }
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
     * 平台审核商品是否可以重新上架
     *
     * @param goodsId 商品ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    @Override
    public void goodsPlatformCheck(Long goodsId, Boolean isAgree) {
        if (isAgree) {  //同意
            //更新商品表字段信息
            Goods goods = goodsMapper.selectById(goodsId);
            if (ToolUtil.isNotEmpty(goods)) {
                goods.setPlatformManage(PlatformManageStatus.ONSHELF.getCode());
                goods.updateById();
            }

            //更新平台审核表信息
            List<PlatformManage> platformManageList = platformManageMapper.selectList(
                    new EntityWrapper<PlatformManage>()
                            .eq("goods_id", goodsId)
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .orderBy("created_time", false)
                            .last("LIMIT 1")
            );
            if (platformManageList != null && platformManageList.size() > 0) {
                PlatformManage platformManage = platformManageList.get(0);
                platformManage.setPlatformManage(PlatformManageStatus.ONSHELF.getCode());
                platformManage.updateById();
            }
        }
        else { //不同意
            //更新平台审核表信息
            List<PlatformManage> platformManageList = platformManageMapper.selectList(
                    new EntityWrapper<PlatformManage>()
                            .eq("goods_id", goodsId)
                            .eq("is_deleted", IsDeleted.NORMAL.getCode())
                            .orderBy("created_time", false)
                            .last("LIMIT 1")
            );
            if (platformManageList != null && platformManageList.size() > 0) {
                PlatformManage platformManage = platformManageList.get(0);
                platformManage.setShopApply(ShopApplyStatus.NO.getCode());
                platformManage.updateById();
            }
        }
    }
}
