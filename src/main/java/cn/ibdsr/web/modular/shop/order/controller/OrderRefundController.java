package cn.ibdsr.web.modular.shop.order.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.ErrorMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.web.modular.shop.order.service.IOrderRefundService;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 订单售后管理控制器
 * @Version V1.0
 * @CreateDate 2019-04-04 15:19:05
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-04 15:19:05    XuZhipeng               类说明
 */
@Controller
@RequestMapping("/shop/orderRefund")
public class OrderRefundController extends BaseController {

    @Autowired
    private IOrderRefundService orderRefundService;

    @Autowired
    private ShopOrderRefundService shopOrderRefundService;

    /**
     * 审核通过
     *
     * @param refundId     退款订单ID
     * @param reviewRemark 审核说明
     * @param shopAddrId   店铺收货地址ID
     * @return
     */
    @RequestMapping(value = "/pass")
    @ResponseBody
    public Object pass(@RequestParam Long refundId, @RequestParam(required = false) String reviewRemark, @RequestParam(required = false) Long shopAddrId) {
        try {
            orderRefundService.pass(refundId, reviewRemark, shopAddrId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorMesTip(e.getMessage());
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 审核不通过
     *
     * @param refundId     退款订单ID
     * @param reviewRemark 审核说明
     * @return
     */
    @RequestMapping(value = "/refusePass")
    @ResponseBody
    public Object refusePass(@RequestParam Long refundId, @RequestParam String reviewRemark) {
        orderRefundService.refusePass(refundId, reviewRemark);
        return SUCCESS_TIP;
    }

    /**
     * 确认收货
     *
     * @param refundId          退款订单ID
     * @param refundLogisticsId 退款退货物流ID
     * @return
     */
    @RequestMapping(value = "/confirmReceipt")
    @ResponseBody
    public Object confirmReceipt(@RequestParam Long refundId, @RequestParam Long refundLogisticsId) {
        try {
            return shopOrderRefundService.confirmReceipt(refundId, refundLogisticsId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorMesTip(e.getMessage());
        }
    }

    /**
     * 商家拒绝收货，此接口是徐金财编写
     *
     * @param refundId 退款订单ID
     * @return SuccessTip
     */
    @RequestMapping(value = "refusereceipt")
    @ResponseBody
    public SuccessTip refuseReceipt(@RequestParam Long refundId, @RequestParam String reason) {
        return shopOrderRefundService.refuseReceipt(refundId, reason);
    }

}
