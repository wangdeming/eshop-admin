package cn.ibdsr.web.modular.shop.hotel.dao;

import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomSettingListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.RoomVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 酒店商家端房间管理Dao
 * @Version V1.0
 * @CreateDate 2019/5/7 15:41
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
public interface RoomDao {

    /**
     * @Description 根据roomId查找房间基本属性
     * @param roomId 房间id
     * @return
     */
    RoomVO getRoomInfo(@Param("roomId") Long roomId);

    /**
     * 分页获取房间列表
     *
     * @param page
     * @param queryDTO 房间查询对象DTO
     *                 status 房间状态（0-未上架，1-销售中；）
     *                 roomName 房间名称
     *                 minPrice 价格范围最低
     *                 maxPrice 价格范围最高
     * @return
     */
    List<Map<String, Object>> list(@Param("page") Page page,
                                   @Param("queryDTO") RoomQueryDTO queryDTO,
                                   @Param("orderByField") String orderByField,
                                   @Param("isAsc") Boolean isAsc);

    /**
     * 批量更新房间状态
     *
     * @param roomIds 房间ID数组
     * @param statusCode 房间状态码（0-下架，1-上架）
     * @param loginUserId 操作用户ID
     * @return
     */
    Integer batchUpdateRoomStatus(@Param("roomIds") Long[] roomIds,
                                   @Param("statusCode") Integer statusCode,
                                   @Param("loginUserId") Long loginUserId);

    /**
     * 删除房间
     * @param roomIds 房间ID数组
     * @param loginUserId 操作用户
     * @return
     */
    Integer deleteRoom(@Param("roomIds")Long[] roomIds, @Param("loginUserId")Long loginUserId, @Param("isDelete")Integer isDelete);

    /**
     * 删除房态设置
     * @param roomId 房间ID
     * @param loginUserId 操作用户
     * @return
     */
    Integer deleteRoomSetting(@Param("roomId")Long roomId, @Param("loginUserId")Long loginUserId, @Param("isDelete")Integer isDelete);

    /**
     * 查询房态设置信息
     * @param roomId 房间ID
     * @return
     */
    RoomSettingListVO roomSettingInfo(@Param("roomId")Long roomId, @Param("date")String date);

}
