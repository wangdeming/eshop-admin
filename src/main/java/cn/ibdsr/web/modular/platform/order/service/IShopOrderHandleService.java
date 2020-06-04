package cn.ibdsr.web.modular.platform.order.service;

import java.util.List;

/**
 * @Description 订单处理Service
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 *
 */
public interface IShopOrderHandleService {

    /**
     * 未支付订单，自动取消
     *
     * @param orderIdList 订单ID集合
     * @return
     */
    void cancelOrder(List<Long> orderIdList) throws Exception;

    /**
     * 订单结算
     *
     * @param orderId 订单ID
     * @return
     */
    void settleOrder(Long orderId) throws Exception;
}
