/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

/**
 * @Description: 房间列表数据对象VO
 * @Version: V1.0
 * @CreateDate: 2019/5/9 8:59
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/9      Zhujingrui               类说明
 *
 */
public class RoomListVO {
    /**
     * 房间ID
     */
    private Long id;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 房间主图路径
     */
    private String mainImg;

    /**
     * 价格
     */
    private String price;

    /**
     * 浏览量
     */
    private Integer viewNum;

    /**
     * 访客数
     */
    private Integer visitorNum;

    /**
     * 创建时间
     */
    private String createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getVisitorNum() {
        return visitorNum;
    }

    public void setVisitorNum(Integer visitorNum) {
        this.visitorNum = visitorNum;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
