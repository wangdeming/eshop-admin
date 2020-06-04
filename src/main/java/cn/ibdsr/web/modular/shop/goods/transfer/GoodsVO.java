/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.goods.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.web.common.persistence.model.GoodsImg;
import cn.ibdsr.web.common.persistence.model.GoodsSku;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description: 商品VO
 * @Version: V1.0
 * @CreateDate: 2019/3/15 10:58
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/15      Zhujingrui               类说明
 *
 */
public class GoodsVO extends BaseDTO {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 商品类别ID
     */
    private Long categoryId;

    /**
     * 商品一二级类目信息
     */
    private List<Long> categoryInfo;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 状态（1-上架；2-下架；）
     */
    private Integer status;

    /**
     * 价格（单位：分）
     */
    private BigDecimal price;

    /**
     * 参考价（划线价，单位：分）
     */
    private BigDecimal referPrice;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 配送方式（1-快递发货；）
     */
    private Integer deliveryType;

    /**
     * 快递运费（单位：分）
     */
    private BigDecimal expressFee;

    /**
     * 规格集合（格式：{"包装":["1KG","2KG","3KB"],"口味":["香辣","麻辣","微辣"]}）
     */
    private String specsList;

    /**
     * 序列号
     */
    private Integer sequence;

    /**
     * 创建人
     */
    private Long createdUser;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改人
     */
    private Long modifiedUser;

    /**
     * 修改时间
     */
    private Date modifiedTime;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 商品图片集
     */
    private JSONArray imageList;

    /**
     * 商品SKU的集
     */
    private JSONArray goodsSkuList;

    /**
     * 商品详情内容
     */
    private String introContent;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(List<Long> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getReferPrice() {
        return referPrice;
    }

    public void setReferPrice(BigDecimal referPrice) {
        this.referPrice = referPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public String getSpecsList() {
        return specsList;
    }

    public void setSpecsList(String specsList) {
        this.specsList = specsList;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public JSONArray getImageList() {
        return imageList;
    }

    public void setImageList(JSONArray imageList) {
        this.imageList = imageList;
    }

    public JSONArray getGoodsSkuList() {
        return goodsSkuList;
    }

    public void setGoodsSkuList(JSONArray goodsSkuList) {
        this.goodsSkuList = goodsSkuList;
    }

    public String getIntroContent() {
        return introContent;
    }

    public void setIntroContent(String introContent) {
        this.introContent = introContent;
    }
}
