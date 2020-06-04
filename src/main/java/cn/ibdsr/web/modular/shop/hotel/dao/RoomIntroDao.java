/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/3/6 16:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/3/6      Zhujingrui               类说明
 *
 */
public interface RoomIntroDao {

    /**
     * @Description 根据房间id获取商品详情描述信息
     * @param roomId 房间id
     * @return 房间详情描述信息
     */
    String getIntroContentByRoomId(@Param("roomId") Long roomId);
}
