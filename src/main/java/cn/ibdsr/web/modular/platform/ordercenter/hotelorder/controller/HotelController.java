package cn.ibdsr.web.modular.platform.ordercenter.hotelorder.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.ordercenter.hotelorder.service.HotelService;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderListVO;
import cn.ibdsr.web.modular.shop.hotel.transfer.HotelOrderQueryDTO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description 订单中心-酒店订单控制器
 * @Version V1.0
 * @CreateDate 2019/5/23 11:34
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/platform/ordercenter")
public class HotelController extends BaseController {

    @Autowired
    private HotelService hotelService;

    private String PREFIX = "/platform/ordercenter/hotelorder/";

    /**
     * 跳转到订单中心-酒店订单首页
     */
    @RequestMapping("/hotelOrder")
    public String hotelOrderIndex() {
        return PREFIX + "hotelOrder.html";
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
    @RequestMapping(value = "/hotelOrderList")
    @ResponseBody
    public Object hotelOrderList(HotelOrderQueryDTO queryDTO) {
        Page<HotelOrderListVO> page = new PageFactory<HotelOrderListVO>().defaultPage();
        List<HotelOrderListVO> hotelOrderListVOList = hotelService.hotelOrderList(page, queryDTO);
        page.setRecords(hotelOrderListVOList);
        return super.packForBT(page);
    }

}
