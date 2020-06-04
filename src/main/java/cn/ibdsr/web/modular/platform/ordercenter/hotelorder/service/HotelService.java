package cn.ibdsr.web.modular.platform.ordercenter.hotelorder.service;

import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Description 订单中心-酒店订单Service
 * @Version V1.0
 * @CreateDate 2019/5/23 11:34
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
public interface HotelService {

    /**
     * 分页获取酒店订单列表
     *
     * @param page
     * @param queryDTO 商品订单查询对象DTO
     *                 orderNo 订单编号
     *                 roomName 房间名称
     *                 roomId 房间Id
     *                 shopId 酒店Id
     *                 shopName 酒店名称
     *                 realname 入住人
     *                 createdTimeStart 下单时间查询起始时间
     *                 createdTimeEnd 下单时间查询终止时间
     *                 checkInDate 入住时间
     *                 status 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     * @return
     */
    List<HotelOrderListVO> hotelOrderList(Page<HotelOrderListVO> page, HotelOrderQueryDTO queryDTO);

}
