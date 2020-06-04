package cn.ibdsr.web.modular.platform.ordercenter.goodsorder.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.service.GoodsOrderService;
import cn.ibdsr.web.modular.platform.ordercenter.goodsorder.transfer.GoodsOrderQueryDTO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Description 订单中心-特产订单控制器
 * @Version V1.0
 * @CreateDate 2019/5/23 11:32
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/5/23      ZhuJingrui            类说明
 */
@Controller
@RequestMapping("/platform/ordercenter")
public class GoodsOrderController extends BaseController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    private String PREFIX = "/platform/ordercenter/goodsorder/";

    /**
     * 跳转到订单中心-特产订单首页
     */
    @RequestMapping("/goodsOrder")
    public String goodsOrderIndex() {
        return PREFIX + "goodsOrder.html";
    }

    /**
     * 跳转到订单中心-特产订单售后详情页
     */
    @RequestMapping("/goodsRefundDetail")
    public String refundDetail() {
        return PREFIX + "goodsRefundDetail.html";
    }

    /**
     * 跳转到订单中心-特产订单详情页
     */
    @RequestMapping("/goodsOrderDetail")
    public String orderDetail() {
        return PREFIX + "goodsOrderDetail.html";
    }

    @RequestMapping(value = "/orderList")
    @ResponseBody
    public Object orderList(GoodsOrderQueryDTO queryDTO){
        Page<OrderVO> page = new PageFactory<OrderVO>().defaultPage();
        List<OrderVO> orderList = goodsOrderService.orderList(page, queryDTO);
        page.setRecords(orderList);
        return new SuccessDataTip(super.packForBT(page));
    }

}
