/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.order.service;

import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.modular.shop.order.transfer.EvaluateDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import cn.ibdsr.web.modular.shop.order.transfer.RefundDetailVO;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/23 17:06
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/23      Zhujingrui               类说明
 *
 */
public interface ShopOrderService {
    /**
     * 订单查询
     * @param page 分页信息
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
    List<OrderVO> orderList(Page<OrderVO> page, String orderNo, String consigneePhone, String consigneeName, String servicePhone,
                            String createdTimeStart, String createdTimeEnd, String goodsName, Integer orderStatus);

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return
     */
    List<OrderDetailVO> orderDetailList(Long orderId);

    /**
     * 统计当前店铺收货地址数量
     * @return
     */
    int addressCount();

    /**
     * 获取快递公司集合
     *
     * @return
     */
    List<Dict> listExpressCompanys();

    /**
     * 订单发货
     *@param orderId 订单id
     *@param orderGoodsIds 订单商品id
     *@param expressCompany 快递公司
     *@param expressNo 快递单号
     * @return
     */
    void deliver(Long orderId, List<Long> orderGoodsIds, String expressCompany, String expressNo);

    /**
     * 确认收货
     *
     * @param orderId 订单ID
     * @return
     */
    void confirm(Long orderId);

    /**
     * 订单中商品状态数量统计，主要是四种状态：1-已发货，2-未发货；3-退款中；4-退款完成
     * @param orderId 订单ID
     * @return
     */
    Map<String, Integer> countOrderGoodsStatus(Long orderId);

    /**
     * 退款详情
     * @param orderGoodsId 订单商品ID
     * @return
     */
    List<RefundDetailVO> getRefundDetail(Long orderGoodsId);

    Boolean checkGoodsIsLastService(Long orderId, Long orderGoodsId);

    /**
     * 订单评价列表
     * @param page 分页信息
     * @param orderNo 订单编号
     * @param goodsName 商品名称
     * @param evaluateWay 评价方式：0-全部；1-默认好评；2-用户自评
     * @param goodsScore 商品星级查询 1-一星；2-二星；...；5-五星
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd 下单时间查询终止时间
     * @return
     */
    List<EvaluateDetailVO> listOrderEvaluate(Page<EvaluateDetailVO> page, String orderNo, String goodsName, Integer evaluateWay,
                                             Integer goodsScore, String createdTimeStart, String createdTimeEnd);

    /**
     * 店铺回复
     * @param goodsEvaluateId 商品评价ID
     * @param replyContent 回复内容
     * @return
     */
    void shopReply(Long goodsEvaluateId, String replyContent);
}
