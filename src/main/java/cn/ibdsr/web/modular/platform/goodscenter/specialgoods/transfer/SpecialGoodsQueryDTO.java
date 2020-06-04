/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.platform.goodscenter.specialgoods.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/23 15:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      Zhujingrui               类说明
 *
 */
public class SpecialGoodsQueryDTO extends BaseDTO {

    /**
     * 商品状态（1-销售中；2-已售罄；3-仓库中；）
     */
    @Verfication(name = "商品状态（1-销售中；2-已售罄；3-仓库中；）", min = 1, max = 3)
    private Integer status;

    /**
     * 平台管理: 0为已下架，1为未下架
     */
    private Integer platformManage;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品Id
     */
    private Long goodsId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品一级类目Id
     */
    private Long firstCategoryId;

    /**
     * 商品二级类目Id
     */
    private Long secondCategoryId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }
}
