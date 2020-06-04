package cn.ibdsr.web.modular.shop.hotel.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.shop.hotel.service.HotelOrderService;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description 酒店商家端-订单管理控制器
 * @Version V1.0
 * @CreateDate 2019/5/7 15:44
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/7      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/shop/hotel")
public class HotelOrderController extends BaseController {

    @Autowired
    private HotelOrderService hotelOrderService;

    private String PREFIX = "/shop/hotel/order/";

    /**
     * 跳转到酒店订单管理首页
     */
    @RequestMapping("/hotelOrderIndex")
    public String hotelOrderIndex() {
        return PREFIX + "hotelOrderList.html";
    }


    /**
     * 跳转到订单详情页
     */
    @RequestMapping("/hotelOrderDetail")
    public String hotelOrderDetail() {
        return PREFIX + "hotelOrderDetail.html";
    }

    /**
     * 分页获取酒店订单列表
     *
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
    @RequestMapping(value = "/hotleOrderList")
    @ResponseBody
    public Object hotleOrderList(HotelOrderQueryDTO queryDTO) {
        Page<HotelOrderListVO> page = new PageFactory<HotelOrderListVO>().defaultPage();
        List<HotelOrderListVO> hotelOrderListVOList = hotelOrderService.hotelOrderList(page, queryDTO);
        page.setRecords(hotelOrderListVOList);
        return super.packForBT(page);
    }

    /**
     * 商家订单确认
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @RequestMapping(value = "/confirm")
    @ResponseBody
    public Object confirm(String hotelOrderId) {
        hotelOrderService.confirm(hotelOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 商家确认入住
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @RequestMapping(value = "/confirmCheckIn")
    @ResponseBody
    public Object confirmCheckIn(String hotelOrderId) {
        hotelOrderService.confirmCheckIn(hotelOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 商家取消订单
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @RequestMapping(value = "/cancelOrder")
    @ResponseBody
    public Object cancelOrder(String hotelOrderId) throws Exception {
        hotelOrderService.cancelOrder(hotelOrderId);
        return SUCCESS_TIP;
    }

    /**
     * 酒店订单详情
     *
     * @param hotelOrderId 酒店订单主键ID
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public SuccessDataTip detail(@RequestParam String hotelOrderId) {
        return hotelOrderService.hotelOrderDetail(hotelOrderId);
    }

}
