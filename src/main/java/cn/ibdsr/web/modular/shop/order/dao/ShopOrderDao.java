/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.order.dao;

import cn.ibdsr.web.modular.shop.order.transfer.EvaluateDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderDetailVO;
import cn.ibdsr.web.modular.shop.order.transfer.OrderVO;
import cn.ibdsr.web.modular.shop.order.transfer.RefundDetailVO;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/4/23 16:43
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/23      Zhujingrui               类说明
 *
 */
public interface ShopOrderDao {
    /**
     * 订单查询
     * @param page 分页信息
     * @param shopId 店铺id
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
    List<OrderVO> orderList(@Param("page") Page<OrderVO> page,
                            @Param("shopId") Long shopId,
                            @Param("orderNo") String orderNo,
                            @Param("consigneePhone") String consigneePhone,
                            @Param("consigneeName") String consigneeName,
                            @Param("servicePhone") String servicePhone,
                            @Param("createdTimeStart") String createdTimeStart,
                            @Param("createdTimeEnd") String createdTimeEnd,
                            @Param("goodsName") String goodsName,
                            @Param("orderStatus")Integer orderStatus);

    /**
     * 订单详情
     * @param orderId 订单ID
     * @return
     */
    List<OrderDetailVO> orderDetailList(@Param("orderId")Long orderId);

    /**
     * 订单评价列表
     * @param page 分页信息
     * @param shopId 店铺id
     * @param orderNo 订单编号
     * @param goodsName 商品名称
     * @param evaluateWay 评价方式：0-全部；1-默认好评；2-用户自评
     * @param goodsScore 商品星级查询 1-一星；2-二星；...；5-五星
     * @param createdTimeStart 下单时间查询起始时间
     * @param createdTimeEnd 下单时间查询终止时间
     * @return
     */
    List<EvaluateDetailVO> listOrderEvaluate(@Param("page") Page<EvaluateDetailVO> page,
                                             @Param("shopId") Long shopId,
                                             @Param("orderNo") String orderNo,
                                             @Param("goodsName") String goodsName,
                                             @Param("evaluateWay") Integer evaluateWay,
                                             @Param("goodsScore") Integer goodsScore,
                                             @Param("createdTimeStart") String createdTimeStart,
                                             @Param("createdTimeEnd") String createdTimeEnd);
}
