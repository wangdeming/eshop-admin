/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.service;

import cn.ibdsr.web.modular.shop.hotel.payment.Payment;

/**
 * @Description: 类功能和用法的说明
 * @Version: V1.0
 * @CreateDate: 2019/7/22 9:02
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/7/22      Zhujingrui               类说明
 *
 */
public interface PayService {
    Object getChannel(Payment payment) throws Exception;

    Object refundChannel(Payment payment) throws Exception;
}
