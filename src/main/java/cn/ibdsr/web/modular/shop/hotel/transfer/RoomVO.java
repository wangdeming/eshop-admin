/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/8 11:02
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/8      Zhujingrui               类说明
 *
 */
public class RoomVO {
    private Long id;
    /**
     * 酒店ID
     */
    private Long shopId;

    /**
     * 名称
     */
    private String name;

    /**
     * 房间主图路径
     */
    private String mainImg;

    /**
     * 早餐，0无早餐，1单人早餐，2双人早餐
     */
    private Integer breakfast;

    /**
     * 宽带，0无宽带，1有线宽带，2WIFI，3WIFI、有线宽带
     */
    private Integer broadband;

    /**
     * 窗户，0无窗，1有窗
     */
    private Integer window;

    /**
     * 面积，以㎡为单位
     */
    private Integer area;

    /**
     * 床宽，以m为单位
     */
    private BigDecimal bedWidth;

    /**
     * 可住多少人
     */
    private Integer person;

    /**
     * 价格，0-1000元之间，以分为单位
     */
    private String price;

    /**
     * 取消方式，0不可取消，1免费取消（当天14：00前可取消）
     */
    private Integer canCancel;

    /**
     * 状态，0未上架，1已上架
     */
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 房间图片集
     */
    private JSONArray imageList;

    /**
     * 房间详情内容
     */
    private String introContent;

    /**
     * 平台管理，0为已下架，1为未下架
     */
    private Integer platformManage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getBroadband() {
        return broadband;
    }

    public void setBroadband(Integer broadband) {
        this.broadband = broadband;
    }

    public Integer getWindow() {
        return window;
    }

    public void setWindow(Integer window) {
        this.window = window;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public BigDecimal getBedWidth() {
        return bedWidth;
    }

    public void setBedWidth(BigDecimal bedWidth) {
        this.bedWidth = bedWidth;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(Integer canCancel) {
        this.canCancel = canCancel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getIntroContent() {
        return introContent;
    }

    public void setIntroContent(String introContent) {
        this.introContent = introContent;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
    }
}
