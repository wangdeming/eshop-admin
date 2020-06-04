package cn.ibdsr.web.modular.platform.goodscenter.hotelroom.service;

import cn.ibdsr.web.modular.platform.goodscenter.hotelroom.transfer.RoomQueryVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingListVO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-酒店房间Service
 * @Version V1.0
 * @CreateDate 2019/5/23 8:42
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface HotelRoomService {

    /**
     * 分页获取房间列表
     *
     * @param page
     * @param roomQueryDTO 房间查询对象DTO
     *                 status 房间状态（0-未上架，1-销售中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopName 酒店名
     *                 shopId 酒店id
     * @return
     */
    List<Map<String,Object>> roomList(Page<RoomQueryVO> page, RoomQueryDTO roomQueryDTO);

    /**
     * 系统下架房间
     *
     * @param roomId 商品ID
     * @param reason 下架原因
     * @return
     */
    void offShelfRoom(Long roomId, String reason);

    /**
     * 查询房态详情
     * @param shopId 酒店ID
     * @param currentDate 当前时间
     * @return
     */
    List<RoomSettingListVO> roomSettingList(Page<RoomSettingListVO> page, Long shopId, String currentDate);

    /**
     * 平台审核房间是否可以重新上架
     *
     * @param roomId 房间ID
     * @param isAgree 是否同意上架；1-同意，0-不同意
     * @return
     */
    void roomPlatformCheck(Long roomId, Boolean isAgree);

}
