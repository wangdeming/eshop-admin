package cn.ibdsr.web.modular.shop.hotel.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

/**
 * @Description 酒店商家端-订单管理Service
 * @Version V1.0
 * @CreateDate 2019/5/7 15:44
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
public interface HotelOrderService {

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
    List<HotelOrderListVO> hotelOrderList(Page<HotelOrderListVO> page, HotelOrderQueryDTO queryDTO);

    /**
     * 商家订单确认
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    void confirm(String hotelOrderId);

    /**
     * 商家确认入住
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    void confirmCheckIn(String hotelOrderId);

    /**
     * 商家取消订单
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    void cancelOrder(String hotelOrderId) throws Exception;

    /**
     * 酒店订单详情
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    SuccessDataTip hotelOrderDetail(String hotelOrderId);

    void settleOrder(String orderId) throws Exception;

    /**
     * 退款
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
//    void refund(String hotelOrderId) throws Exception;
}
