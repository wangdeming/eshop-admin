/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 房态设置dto
 * @Version: V1.0
 * @CreateDate: 2019/5/13 17:20
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/13      Zhujingrui               类说明
 *
 */
public class RoomSettingDTO extends BaseDTO {
    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 日期
     */
    private String date;

    /**
     * 查询起始日期
     */
    private String startDate;

    /**
     * 查询结束日期
     */
    private String endDate;

    /**
     * 工作日
     */
    private List<Integer> weekdayList;

    /**
     * 房间数量
     */
    private Integer number;

    /**
     * 价格，0-100000元之间，以分为单位
     */
    private BigDecimal price;

    /**
     * 状态，0关，1开
     */
    private Integer status;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Integer> getWeekdayList() {
        return weekdayList;
    }

    public void setWeekdayList(List<Integer> weekdayList) {
        this.weekdayList = weekdayList;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
