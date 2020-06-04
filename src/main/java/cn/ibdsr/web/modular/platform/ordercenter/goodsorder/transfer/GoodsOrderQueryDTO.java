/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.platform.ordercenter.goodsorder.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/29 8:46
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/29      Zhujingrui               类说明
 *
 */
public class GoodsOrderQueryDTO extends BaseDTO {

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 收件人姓名
     */
    private String consigneeName;

    /**
     * 收件人手机号
     */
    private String consigneePhone;

    /**
     * 售后人手机号
     */
    private String servicePhone;

    /**
     *  下单时间查询起始时间
     */
    private String createdTimeStart;

    /**
     *  下单时间查询终止时间
     */
    private String createdTimeEnd;

    /**
     *  查看订单的状态（0-全部；1-待付款；2-待发货；3-待收货；4-交易完成；5-已取消；6-售后中；7-交易关闭）
     */
    private  Integer orderStatus;

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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getCreatedTimeStart() {
        return createdTimeStart;
    }

    public void setCreatedTimeStart(String createdTimeStart) {
        this.createdTimeStart = createdTimeStart;
    }

    public String getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(String createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
