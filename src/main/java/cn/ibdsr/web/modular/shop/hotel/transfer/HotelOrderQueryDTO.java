/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/13 18:30
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/13      Zhujingrui               类说明
 *
 */
public class HotelOrderQueryDTO extends BaseDTO {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 名称
     */
    private String roomName;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 下单时间起始查询
     */
    private String createdTimeStart;

    /**
     * 下单时间起始查询
     */
    private String createdTimeEnd;

    /**
     * 入住时间
     */
    private String checkInDate;

    /**
     * 入住人
     */
    private String realname;

    /**
     * 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     */
    private Integer status;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
