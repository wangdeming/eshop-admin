/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.service;

import cn.ibdsr.web.common.persistence.model.HotelOrderRefund;
import cn.ibdsr.web.common.persistence.model.Payment;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;

import java.util.Map;

/**
 * @Description: 酒店订单退款管理
 * @Version: V1.0
 * @CreateDate: 2019/7/9 15:22
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/7/9      Zhujingrui               类说明
 *
 */
public interface HotelOrderRefundService {

    /**
     * 执行退款，调用退款接口
     *
     * @param hotelOrderId 酒店订单id
     */
    void refund(String hotelOrderId) throws Exception;

}
