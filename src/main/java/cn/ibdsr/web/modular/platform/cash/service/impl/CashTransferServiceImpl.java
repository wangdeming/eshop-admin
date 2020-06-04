package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.web.common.constant.state.cash.PlatformBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.cash.ShopBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.order.OrderCashTransSrc;
import cn.ibdsr.web.common.constant.state.order.OrderCashType;
import cn.ibdsr.web.modular.platform.cash.service.ICashChangeService;
import cn.ibdsr.web.modular.platform.cash.service.ICashTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description 资金流动业务Service
 * @Version V1.0
 * @CreateDate 2019-04-26 14:12:11
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-26 14:12:11    XuZhipeng               类说明
 */
@Service
public class CashTransferServiceImpl implements ICashTransferService {

    @Autowired
    private ICashChangeService cashChangeService;

    /**
     * 用户确认收货：待出账金额-（shop_order_cash_flow）；待到账金额+（shop_order_cash_flow）
     *
     * @param shopId  店铺ID
     * @param orderId 订单ID
     * @param amount  变动金额
     * @return
     */
    @Override
    public void userConfirmTransfer(Long shopId, Long orderId, BigDecimal amount, Integer shopType) {
        // 待出账金额-
        cashChangeService.debitOrderCash(shopId, String.valueOf(orderId), amount, OrderCashType.OUTACC_AMOUNT.getCode(), OrderCashTransSrc.CONFIRM_RECEIVE.getCode(), "用户确认收货", shopType);

        // 待到账金额+
        cashChangeService.creditOrderCash(shopId, String.valueOf(orderId), amount, OrderCashType.INCOME_AMOUNT.getCode(), OrderCashTransSrc.CONFIRM_RECEIVE.getCode(), "用户确认收货", shopType);
    }

    /**
     * 用户退款（支付后，商品未发货）：待出账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
     *
     * @param shopId  店铺ID
     * @param orderId 订单ID
     * @param amount  变动金额
     * @return
     */
    @Override
    public void userRefundTransfer(Long shopId, Long orderId, BigDecimal amount, Integer shopType) {
        // 待出账金额-
        cashChangeService.debitOrderCash(shopId, String.valueOf(orderId), amount, OrderCashType.OUTACC_AMOUNT.getCode(), OrderCashTransSrc.REFUND.getCode(), "用户退款", shopType);

        // 平台余额-
        cashChangeService.debitPlatformBalance(amount, PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode(), String.valueOf(orderId), "用户退款");
    }

    /**
     * 售后退款：待到账金额-（shop_order_cash_flow）；平台余额-（platform_balance_flow）
     *
     * @param shopId  店铺ID
     * @param orderId 订单ID
     * @param amount  变动金额
     * @return
     */
    @Override
    public void serviceRefundTransfer(Long shopId, Long orderId, BigDecimal amount, Integer shopType) {
        // 待到账金额-
        cashChangeService.debitOrderCash(shopId, String.valueOf(orderId), amount, OrderCashType.INCOME_AMOUNT.getCode(), OrderCashTransSrc.REFUND.getCode(), "用户退款", shopType);

        // 平台余额-
        cashChangeService.debitPlatformBalance(amount, PlatformBalanceFlowTransSrc.GOODS_REFUND.getCode(), String.valueOf(orderId), "用户退款");
    }

    /**
     * 订单结算：待到账金额-（shop_order_cash_flow）；店铺余额+（shop_balance_flow）
     *
     * @param shopId       店铺ID
     * @param orderId       订单ID
     * @param orderSettlementId      外部关联ID（shop_order_settlement结算表主键ID）
     * @param accAmount    待到账金额
     * @param settleAmount 结算金额
     * @return
     */
    @Override
    public void orderSettleTransfer(Long shopId, String orderId, String orderSettlementId, BigDecimal accAmount, BigDecimal settleAmount, Integer shopType) {
        // 店铺待到账金额-
        cashChangeService.debitOrderCash(shopId, orderId, accAmount, OrderCashType.INCOME_AMOUNT.getCode(), OrderCashTransSrc.ORDER_SETTLE.getCode(), "订单结算", shopType);

        // 店铺余额+
        cashChangeService.creditShopBalance(shopId, settleAmount, ShopBalanceFlowTransSrc.ORDER_ADD.getCode(), orderSettlementId, "订单结算");
    }

    /**
     * 审核不通过：店铺余额+（shop_balance_flow）
     *
     * @param shopId       店铺ID
     * @param withdrawalId 提现ID
     * @param amount       提现金额
     * @return
     */
    @Override
    public void withdrawalRefusePassTransfer(Long shopId, String withdrawalId, BigDecimal amount) {
        // 店铺余额+
        cashChangeService.creditShopBalance(
                shopId,
                amount,
                ShopBalanceFlowTransSrc.WITHDRAWAL_ADD.getCode(),
                withdrawalId,
                "提现审核不通过返还余额");
    }

    /**
     * 提现确认打款：平台余额-（platform_balance_flow）
     *
     * @param withdrawalId 提现ID
     * @param amount       提现金额
     * @return
     */
    @Override
    public void withdrawalConfirmTransfer(Long withdrawalId, BigDecimal amount) {
        // 平台余额-
        cashChangeService.debitPlatformBalance(
                amount,
                PlatformBalanceFlowTransSrc.WITHDRAWAL.getCode(),
                String.valueOf(withdrawalId),
                "店铺提现确认打款");
    }
}
