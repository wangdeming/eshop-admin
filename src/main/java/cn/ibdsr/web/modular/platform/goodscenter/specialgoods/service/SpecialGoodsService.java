package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.service;

import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsListVO;
import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-特产商品Service
 * @Version V1.0
 * @CreateDate 2019/5/23 16:46
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface SpecialGoodsService {

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
    List<Map<String, Object>> goodsList(Page page, SpecialGoodsQueryDTO queryDTO);

    /**
     * 系统下架商品
     *
     * @param goodsId 商品ID
     * @param reason 下架原因
     * @return
     */
    void offShelf(Long goodsId, String reason);

    /**
     * 平台审核商品是否可以重新上架
     *
     * @param goodsId 商品ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    void goodsPlatformCheck(Long goodsId, Boolean isAgree);

}
