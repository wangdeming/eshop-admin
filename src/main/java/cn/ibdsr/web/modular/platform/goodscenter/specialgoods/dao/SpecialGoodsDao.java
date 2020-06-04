package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.dao;

import cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer.SpecialGoodsQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-特产商品Dao
 * @Version V1.0
 * @CreateDate 2019/5/23 16:46
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface SpecialGoodsDao {

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
    List<Map<String, Object>> goodsList(@Param("page") Page page,
                                        @Param("queryDTO") SpecialGoodsQueryDTO queryDTO,
                                        @Param("orderByField") String orderByField,
                                        @Param("isAsc") Boolean isAsc);
}
