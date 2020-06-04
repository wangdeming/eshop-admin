package cn.ibdsr.web.modular.shop.hotel.service;

import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.common.persistence.model.Room;
import cn.ibdsr.web.modular.shop.hotel.transfer.*;
import com.baomidou.mybatisplus.plugins.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 酒店商家端房间管理Service
 * @Version V1.0
 * @CreateDate 2019/5/7 15:41
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
public interface RoomService {

    /**
     * @Description 新增房间
     * @param room 房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content 房间详情描述
     * @return
     */
    SuccessTip insert(Room room, String imageListJsonString, String content);

    /**
     * @Description 编辑房间
     *
     * @param roomId 房间id
     * @param room 房间对象
     * @param imageListJsonString 房间图片Json字符串
     * @param content 房间详情描述
     * @return
     */
    SuccessTip update(Long roomId, Room room, String imageListJsonString, String content);

    /**
     * @Description 根据roomId查找房间基本信息
     * @param roomId 房间id
     * @return
     */
    RoomVO getRoomInfo(Long roomId);

    /**
     * 分页查询房间列表
     *
     * @param page
     * @param roomQueryDTO 商品查询对象DTO
     *                      status 商品状态（0-未上架，1-销售中；）
     *                      roomName 商品名称
     *                      minPrice 价格范围最低
     *                      maxPrice 价格范围最高
     * @return
     */
    List<Map<String,Object>> list(Page<RoomListVO> page, RoomQueryDTO roomQueryDTO);

    /**
     * 批量更新商品状态：上架、下架
     *
     * @param roomIds 房间ID数组
     * @param statusCode 房间状态（0-下架，1-上架；）
     */
    void batchUpdateRoomStatus(Long[] roomIds, Integer statusCode);

    /**
     * 被系统下架的房间批量申请重新上架
     *
     * @param roomIds 房间ID数组
     */
    void applyRoomOnShelf(Long[] roomIds);

    /**
     * 删除房间
     * @param roomIds 房间ID数组
     */
    void deleteRoom(Long[] roomIds);

    /**
     * 查询房态设置列表
     * @param roomId 房间ID
     * @param currentDate 当前时间
     * @return
     */
    List<RoomSettingListVO> roomSettingList(Page<RoomSettingListVO> page, Long roomId, String currentDate);


    /**
     * 单个房态设置
     * @param roomSettingDTO 房态设置对象
     *  roomId 房间ID
     *  date 日期
     *  number 房间数量
     *  price 房间价格
     *  status 房间开关状态
     * @return
     */
    SuccessTip setRoom(RoomSettingDTO roomSettingDTO);

    /**
     * 批量房态设置
     * @param roomSettingDTO 房态设置对象
     *  roomId 房间ID
     *  startDate 起始日期
     *  endDate 起始日期
     *  weekday 工作日： 1－7分别代表周一到周日
     *  number 房间数量
     *  price 房间价格
     *  status 房间开关状态
     * @return
     */
    SuccessTip batchSetRoom(RoomSettingDTO roomSettingDTO);

    RoomSettingListVO defaultRoomSet (Room room, List<String> dayList);

    RoomSettingListVO getRoomSetInfo(Room room, List<String> dayList);

}
