package cn.ibdsr.web.modular.shop.order.service;

import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.base.tips.SuccessTip;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.web.common.persistence.model.Payment;
import cn.ibdsr.web.common.persistence.model.ShopOrderRefund;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 订单退款
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/3 17:12
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/3 xujincai init
 */
public interface ShopOrderRefundService {

    /**
     * 获取支付订单信息
     *
     * @param orderId
     * @return
     */
    Payment payment(Long orderId);

    /**
     * 执行退款，调用退款接口
     *
     * @param shopOrderRefund
     * @param payment
     */
    Map<String, String> refund(ShopOrderRefund shopOrderRefund, Payment payment) throws Exception;

    void changeGoodsStock(Long goodsId, Long goodsSkuId, int number, ShiroUser user, Date date);

    SuccessMesTip passRefund(Long shopOrderRefundId) throws Exception;

    SuccessMesTip confirmReceipt(Long shopOrderRefundId, Long shopOrderRefundLogisticsId) throws Exception;

    SuccessTip refuseReceipt(Long shopOrderRefundId, String reason);

    void revoke(Long shopOrderRefundId);
}
