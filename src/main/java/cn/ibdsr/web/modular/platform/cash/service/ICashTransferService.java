package cn.ibdsr.web.modular.platform.cash.service;

import java.math.BigDecimal;

/**
 * @Description 资金流动业务Service
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 */
public interface ICashTransferService {

    void userConfirmTransfer(Long shopId, Long orderId, BigDecimal amount, Integer shopType);

    /**
     * 用户退款（支付后，商品未发货）：待出账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
     *
     * @param shopId  店铺ID
     * @param tradeId 外部关联ID（如订单ID）
     * @param amount  变动金额
     * @return
     */
    void userRefundTransfer(Long shopId, Long tradeId, BigDecimal amount, Integer shopType);

    /**
     * 售后退款：待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
     *
     * @param shopId  店铺ID
     * @param orderId 订单ID
     * @param amount  变动金额
     * @return
     */
    void serviceRefundTransfer(Long shopId, Long orderId, BigDecimal amount, Integer shopType);

    /**
     * 订单结算：待到账金额-（shop_order_cash_flow）；店铺余额+（shop_balance_flow）
     *
     * @param shopId            店铺ID
     * @param orderId           订单ID
     * @param orderSettlementId 外部关联ID（shop_order_settlement结算表主键ID）
     * @param accAmount         待到账金额
     * @param settleAmount      结算金额
     * @return
     */
    void orderSettleTransfer(Long shopId, String orderId, String orderSettlementId, BigDecimal accAmount, BigDecimal settleAmount, Integer shopType);

    /**
     * 审核不通过：店铺余额+（shop_balance_flow）
     *
     * @param shopId       店铺ID
     * @param withdrawalId 提现ID
     * @param amount       提现金额
     * @return
     */
    void withdrawalRefusePassTransfer(Long shopId, String withdrawalId, BigDecimal amount);

    /**
     * 提现确认打款：平台余额-（platform_balance_flow）
     *
     * @param withdrawalId 提现ID
     * @param amount       提现金额
     * @return
     */
    void withdrawalConfirmTransfer(Long withdrawalId, BigDecimal amount);
}
