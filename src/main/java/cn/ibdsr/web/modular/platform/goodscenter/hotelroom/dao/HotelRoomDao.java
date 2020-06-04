package cn.ibdsr.web.modular.platform.goodscenter.hotelroom.dao;

import cn.ibdsr.web.modular.shop.hotel.transfer.RoomQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description 商品中心-酒店房间Dao
 * @Version V1.0
 * @CreateDate 2019/5/23 8:42
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface HotelRoomDao {

    /**
     * 分页获取房间列表
     *
     * @param page
     * @param queryDTO 房间查询对象DTO
     *                 status 房间状态（0-未上架，1-销售中；）
     *                 platformManage 平台管理: 0为已下架，1为未下架
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopName 酒店名
     *                 shopId 酒店id
     * @return
     */
    List<Map<String, Object>> roomList(@Param("page") Page page,
                                   @Param("queryDTO") RoomQueryDTO queryDTO,
                                   @Param("orderByField") String orderByField,
                                   @Param("isAsc") Boolean isAsc);

}
