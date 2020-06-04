package cn.ibdsr.web.modular.platform.cash.service;

import java.math.BigDecimal;

/**
 * @Description 资金变动Service
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 *
 */
public interface ICashChangeService {

    /**
     * 平台余额变动+
     *
     * @param amount 变动金额
     * @param transSrc 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
     * @param tradeId 外部关联ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark 流水备注
     * @return
     */
    void creditPlatformBalance(BigDecimal amount, Integer transSrc, String tradeId, String remark);

    /**
     * 平台余额变动-
     *
     * @param amount 变动金额
     * @param transSrc 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
     * @param tradeId 外部关联ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark 流水备注
     */
    void debitPlatformBalance(BigDecimal amount, Integer transSrc, String tradeId, String remark);

    /**
     * 店铺余额变动+
     *
     * @param shopId 店铺ID
     * @param amount 变动金额
     * @param transSrc 交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）
     * @param tradeId 外部关联ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark 流水备注
     * @return
     */
    void creditShopBalance(Long shopId, BigDecimal amount, Integer transSrc, String tradeId, String remark);

    /**
     * 店铺余额变动-
     *
     * @param shopId 店铺ID
     * @param amount 变动金额
     * @param transSrc 交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）
     * @param tradeId 外部关联ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark 流水备注
     */
    void debitShopBalance(Long shopId, BigDecimal amount, Integer transSrc, String tradeId, String remark);

    /**
     * 订单资金变动+
     *
     * @param shopId 店铺ID
     * @param orderId 订单ID
     * @param amount 变动金额
     * @param cashType 资金类型（1-待出账金额；2-待到账金额；）
     * @param transSrc 交易来源（1-用户已付款（待出账金额+）；
     *                 2-用户退款（待出账金额-）；
     *                 3-用户已收货=交易完成（待出账金额-、待到账金额+）；
     *                 4-用户售后退款（待到账金额-）；
     *                 5-订单已结算（待到账金额-、店铺余额+）；）
     * @param remark 流水备注
     * @return
     */
    void creditOrderCash(Long shopId, String orderId, BigDecimal amount, Integer cashType, Integer transSrc, String remark, Integer shopType);

    /**
     * 订单资金变动-
     *
     * @param shopId 店铺ID
     * @param orderId 订单ID
     * @param amount 变动金额
     * @param cashType 资金类型（1-待出账金额；2-待到账金额；）
     * @param transSrc 交易来源（1-用户已付款（待出账金额+）；
     *                 2-用户退款（待出账金额-）；
     *                 3-用户已收货（待出账金额-、待到账金额+）；
     *                 4-用户售后退款（待到账金额-）；
     *                 5-订单已结算（待到账金额-、店铺余额+）；）
     * @param remark 流水备注
     * @return
     */
    void debitOrderCash(Long shopId, String orderId, BigDecimal amount, Integer cashType, Integer transSrc, String remark, Integer shopType);
}
