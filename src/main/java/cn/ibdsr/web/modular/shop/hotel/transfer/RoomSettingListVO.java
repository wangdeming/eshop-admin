/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.transfer;

import java.util.List;
import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/5/10 14:08
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/10      Zhujingrui               类说明
 *
 */
public class RoomSettingListVO {

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间名称
     */
    private String name;

    /**
     * 房态设置集合
     */
    private List<Map<String, Object>> roomSettingList;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Map<String, Object>> getRoomSettingList() {
        return roomSettingList;
    }

    public void setRoomSettingList(List<Map<String, Object>> roomSettingList) {
        this.roomSettingList = roomSettingList;
    }
}
