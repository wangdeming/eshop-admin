/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.order.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.web.common.constant.factory.PageFactory;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.core.util.CommonUtils;
import cn.ibdsr.web.modular.shop.order.service.ShopOrderService;
import cn.ibdsr.web.modular.shop.order.transfer.EvaluateDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import cn.ibdsr.web.modular.shop.order.transfer.RefundDetailVO;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/23 18:14
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/23      Zhujingrui               类说明
 *
 */
@Controller
@RequestMapping(value = "shop/order")
public class ShopOrderController extends BaseController {

    private String PREFIX = "/shop/order/";

    @Autowired
    private ShopOrderService shopOrderService;

    /**
     * 跳转到订单管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shopOrder.html";
    }

    /**
     * 跳转到售后审核
     */
    @RequestMapping("/refund")
    public String refund() {
        return PREFIX + "refund.html";
    }

    /**
     * 跳转到发货页面
     */
    @RequestMapping("/delivery/{orderId}")
    public String delivery() {
        if (shopOrderService.addressCount() < 1){
            throw new BussinessException(BizExceptionEnum.SHOP_DELIVERY_ADDRESS_NOT_EXIST);
        }
        return PREFIX + "delivery.html";
    }

    /**
     * 跳转到订单详情页
     */
    @RequestMapping("/detail")
    public String detail() {
        return PREFIX + "detail.html";
    }

    /**
     * 跳转到评价管理页面
     */
    @RequestMapping("/orderEvaluateList")
    public String orderEvaluateList() {
        return PREFIX + "orderEvaluateList.html";
    }

    /**
     * 订单查询
     * @param orderNo 订单编号
     * @param consigneePhone 收件人手机号
     * @param consigneeName 收件人姓名
     * @param servicePhone 售后人手机号
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd 下单时间查询终止时间
     * @param goodsName 商品名称
     * @param orderStatus 查看订单的状态（0-全部；1-待付款；2-待发货；3-待收货；4-交易完成；5-已取消；6-售后中；7-交易关闭）
     * @return 返回订单列表
     */
    @RequestMapping(value = "/orderList")
    @ResponseBody
    public Object orderList(@RequestParam(required=false) String orderNo,
                            @RequestParam(required=false) String consigneePhone,
                            @RequestParam(required=false) String consigneeName,
                            @RequestParam(required=false) String servicePhone,
                            @RequestParam(required=false) String createdTimeStart,
                            @RequestParam(required=false) String createdTimeEnd,
                            @RequestParam(required=false) String goodsName,
                            @RequestParam(required=false) Integer orderStatus) {
        Page<OrderVO> page = new PageFactory<OrderVO>().defaultPage();
        List<OrderVO> orderList = shopOrderService.orderList(page, orderNo, consigneePhone, consigneeName, servicePhone,createdTimeStart, createdTimeEnd, goodsName, orderStatus);
        page.setRecords(orderList);
        return new SuccessDataTip(super.packForBT(page));
    }

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping(value = "/orderDetail")
    @ResponseBody
    public Object orderDetail(Long orderId) {
        List<OrderDetailVO> orderDetailList = shopOrderService.orderDetailList(orderId);
        return new SuccessDataTip(orderDetailList);
    }

    /**
     * 获取快递公司集合
     *
     * @return
     */
    @RequestMapping(value = "/listExpressCompanys")
    @ResponseBody
    public Object listExpressCompanys() {
        List<Dict> resultList = shopOrderService.listExpressCompanys();
        return new SuccessDataTip(resultList);
    }

    /**
     * 订单发货
     *@param orderId 订单id
     *@param orderGoodsIds 订单商品id
     *@param expressCompany 快递公司
     *@param expressNo 快递单号
     * @return
     */
    @RequestMapping(value = "/deliver")
    @ResponseBody
    public Object deliver(@RequestParam Long orderId, @RequestParam List<Long> orderGoodsIds, @RequestParam String expressCompany, @RequestParam String expressNo){
        shopOrderService.deliver(orderId, orderGoodsIds, expressCompany, expressNo);
        return super.SUCCESS_TIP;
    }

    /**
     * 订单中商品状态数量统计，主要是四种状态：1-已发货，2-未发货；3-退款中；4-退款完成
     * @param orderId 订单ID
     * @return
     */
    @RequestMapping(value = "/countOrderGoodsStatus")
    @ResponseBody
    public Object countOrderGoodsStatus(@RequestParam Long orderId){
        Map<String, Integer> result = shopOrderService.countOrderGoodsStatus(orderId);
        return new SuccessDataTip(result);
    }

    /**
     * 退款详情
     * @param orderGoodsId 订单商品ID
     * @return
     */
    @RequestMapping(value = "/getRefundDetailList")
    @ResponseBody
    public Object getRefundDetail(Long orderGoodsId) {
        List<RefundDetailVO> refundDetailVOList = shopOrderService.getRefundDetail(orderGoodsId);
        return new SuccessDataTip(refundDetailVOList);
    }

    /**
     * 订单评价列表
     * @param orderNo 订单编号
     * @param goodsName 商品名称
     * @param evaluateWay 评价方式：0-全部；1-默认好评；2-用户自评
     * @param goodsScore 商品星级查询 1-一星；2-二星；...；5-五星
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd 下单时间查询终止时间
     * @return
     */
    @RequestMapping(value = "/listOrderEvaluate")
    @ResponseBody
    public Object listOrderEvaluate(String orderNo, String goodsName, Integer evaluateWay, Integer goodsScore,
                                    String createdTimeStart, String createdTimeEnd){

        Page<EvaluateDetailVO> page = new PageFactory<EvaluateDetailVO>().defaultPage();

        List<EvaluateDetailVO> evaluateDetailVOList = shopOrderService.listOrderEvaluate(page, orderNo, goodsName,
                evaluateWay, goodsScore, createdTimeStart, createdTimeEnd);
        page.setRecords(evaluateDetailVOList);

        return new SuccessDataTip(super.packForBT(page));
    }

    /**
     * 店铺回复
     * @param goodsEvaluateId 商品评价ID
     * @param replyContent 回复内容
     * @return
     */
    @RequestMapping(value = "/shopReply")
    @ResponseBody
    public Object shopReply(Long goodsEvaluateId, String replyContent) {
        shopOrderService.shopReply(goodsEvaluateId, replyContent);
        return super.SUCCESS_TIP;
    }

}
