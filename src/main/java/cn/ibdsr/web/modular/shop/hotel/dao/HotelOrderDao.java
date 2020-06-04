package cn.ibdsr.web.modular.shop.hotel.dao;

import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 酒店商家端-订单管理Dao
 * @Version V1.0
 * @CreateDate 2019/5/7 15:44
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
public interface HotelOrderDao {

    /**
     * 分页获取酒店订单列表
     *
     * @param page
     * @param queryDTO 商品订单查询对象DTO
     *                 orderNo 订单编号
     *                 roomName 房间名称
     *                 realname 入住人
     *                 createdTimeStart 下单时间查询起始时间
     *                 createdTimeEnd 下单时间查询终止时间
     *                 checkInDate 入住时间
     *                 status 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     * @return
     */
    List<HotelOrderListVO> hotleOrderList(@Param("page") Page<HotelOrderListVO> page, @Param("queryDTO") HotelOrderQueryDTO queryDTO);


}
