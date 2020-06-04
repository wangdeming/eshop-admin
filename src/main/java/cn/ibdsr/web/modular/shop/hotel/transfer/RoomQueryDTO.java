/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.Verfication;

import java.math.BigDecimal;

/**
 * @Description: 房间列表查询DTO
 * @Version: V1.0
 * @CreateDate: 2019/5/9 8:58
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/9      Zhujingrui               类说明
 *
 */
public class RoomQueryDTO extends BaseDTO {

    /**
     * 商品状态（0-未上架，1-销售中）
     */
    @Verfication(name = "商品状态（0-未上架，1-销售中）", min = 0, max = 1)
    private Integer status;

    /**
     * 平台管理: 0为已下架，1为未下架
     */
    private Integer platformManage;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 最低价格
     */
    @Verfication(name = "最低价格", min = 0)
    private BigDecimal minPrice;

    /**
     * 最高价格
     */
    @Verfication(name = "最高价格", min = 0)
    private BigDecimal maxPrice;

    /**
     * 店铺ID
     */
    private Long shopId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getPlatformManage() {
        return platformManage;
    }

    public void setPlatformManage(Integer platformManage) {
        this.platformManage = platformManage;
    }
}
