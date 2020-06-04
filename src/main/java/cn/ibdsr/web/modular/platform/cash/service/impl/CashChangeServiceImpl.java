package cn.ibdsr.web.modular.platform.cash.service.impl;

import cn.ibdsr.web.common.persistence.dao.PlatformBalanceFlowMapper;
import cn.ibdsr.web.common.persistence.dao.ShopBalanceFlowMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderCashFlowMapper;
import cn.ibdsr.web.common.persistence.model.PlatformBalanceFlow;
import cn.ibdsr.web.common.persistence.model.ShopBalanceFlow;
import cn.ibdsr.web.common.persistence.model.ShopOrderCashFlow;
import cn.ibdsr.web.modular.platform.cash.service.ICashChangeService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description 资金变动Service
 * @Version V1.0
 * @CreateDate 2019-04-23 14:12:11
 * <p>
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-23 14:12:11    XuZhipeng               类说明
 */
@Service
public class CashChangeServiceImpl implements ICashChangeService {

    @Autowired
    private PlatformBalanceFlowMapper platformBalanceFlowMapper;

    @Autowired
    private ShopBalanceFlowMapper shopBalanceFlowMapper;

    @Autowired
    private ShopOrderCashFlowMapper shopOrderCashFlowMapper;

    /**
     * 平台余额变动+
     *
     * @param amount   变动金额
     * @param transSrc 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
     * @param tradeId  外部关联ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark   流水备注
     * @return
     */
    @Override
    public void creditPlatformBalance(BigDecimal amount, Integer transSrc, String tradeId, String remark) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        BigDecimal balance = amount;                  // 本期余额
        BigDecimal preBalance = BigDecimal.ZERO;      // 上期余额
        // 判断平台是否存在余额流水记录
        List<PlatformBalanceFlow> flowList = platformBalanceFlowMapper.selectList(
                new EntityWrapper<PlatformBalanceFlow>()
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            preBalance = flowList.get(0).getBalance();
            balance = preBalance.add(amount);
        }
        PlatformBalanceFlow newFlow = new PlatformBalanceFlow();
        newFlow.setTransSrc(transSrc);
        newFlow.setTradeId(tradeId);
        newFlow.setBalance(balance);
        newFlow.setCreditAmount(amount);
        newFlow.setDebitAmount(BigDecimal.ZERO);
        newFlow.setPreBalance(preBalance);
        newFlow.setRemark(remark);
        newFlow.setCreatedTime(new Date());
        newFlow.insert();
    }

    /**
     * 平台余额变动-
     *
     * @param amount   变动金额
     * @param transSrc 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
     * @param tradeId  外部关联ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark   流水备注
     */
    @Override
    public void debitPlatformBalance(BigDecimal amount, Integer transSrc, String tradeId, String remark) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        // 判断查询平台是否存在余额流水记录
        List<PlatformBalanceFlow> flowList = platformBalanceFlowMapper.selectList(
                new EntityWrapper<PlatformBalanceFlow>()
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            BigDecimal preBalance = flowList.get(0).getBalance();   // 上期余额
            BigDecimal balance = preBalance.subtract(amount);       // 本期余额
            PlatformBalanceFlow newFlow = new PlatformBalanceFlow();
            newFlow.setTransSrc(transSrc);
            newFlow.setTradeId(tradeId);
            newFlow.setBalance(balance);
            newFlow.setCreditAmount(BigDecimal.ZERO);
            newFlow.setDebitAmount(amount);
            newFlow.setPreBalance(preBalance);
            newFlow.setRemark(remark);
            newFlow.setCreatedTime(new Date());
            newFlow.insert();
        }
    }

    /**
     * 店铺余额变动+
     *
     * @param shopId   店铺ID
     * @param amount   变动金额
     * @param transSrc 交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）
     * @param tradeId  外部关联ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark   流水备注
     * @return
     */
    @Override
    public void creditShopBalance(Long shopId, BigDecimal amount, Integer transSrc, String tradeId, String remark) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        BigDecimal balance = amount;                  // 本期余额
        BigDecimal preBalance = BigDecimal.ZERO;      // 上期余额
        // 判断该店铺是否存在余额流水记录
        List<ShopBalanceFlow> flowList = shopBalanceFlowMapper.selectList(
                new EntityWrapper<ShopBalanceFlow>()
                        .eq("shop_id", shopId)
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            preBalance = flowList.get(0).getBalance();
            balance = preBalance.add(amount);
        }
        ShopBalanceFlow newFlow = new ShopBalanceFlow();
        newFlow.setShopId(shopId);
        newFlow.setTransSrc(transSrc);
        newFlow.setTradeId(tradeId);
        newFlow.setBalance(balance);
        newFlow.setCreditAmount(amount);
        newFlow.setDebitAmount(BigDecimal.ZERO);
        newFlow.setPreBalance(preBalance);
        newFlow.setRemark(remark);
        newFlow.setCreatedTime(new Date());
        newFlow.insert();
    }

    /**
     * 店铺余额变动-
     *
     * @param shopId   店铺ID
     * @param amount   变动金额
     * @param transSrc 交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）
     * @param tradeId  外部关联ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）
     * @param remark   流水备注
     */
    @Override
    public void debitShopBalance(Long shopId, BigDecimal amount, Integer transSrc, String tradeId, String remark) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        // 判断查询该店铺是否存在余额流水记录
        List<ShopBalanceFlow> flowList = shopBalanceFlowMapper.selectList(
                new EntityWrapper<ShopBalanceFlow>()
                        .eq("shop_id", shopId)
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            BigDecimal preBalance = flowList.get(0).getBalance();   // 上期余额
            BigDecimal balance = preBalance.subtract(amount);       // 本期余额
            ShopBalanceFlow newFlow = new ShopBalanceFlow();
            newFlow.setShopId(shopId);
            newFlow.setTransSrc(transSrc);
            newFlow.setTradeId(tradeId);
            newFlow.setBalance(balance);
            newFlow.setCreditAmount(BigDecimal.ZERO);
            newFlow.setDebitAmount(amount);
            newFlow.setPreBalance(preBalance);
            newFlow.setRemark(remark);
            newFlow.setCreatedTime(new Date());
            newFlow.insert();
        }
    }

    /**
     * 订单资金变动+
     *
     * @param shopId   店铺ID
     * @param orderId  订单ID
     * @param amount   变动金额
     * @param cashType 资金类型（1-待出账金额；2-待到账金额；）
     * @param transSrc 交易来源（1-用户已付款（待出账金额+）；
     *                 2-用户退款（待出账金额-）；
     *                 3-用户已收货=交易完成（待出账金额-、待到账金额+）；
     *                 4-用户售后退款（待到账金额-）；
     *                 5-订单已结算（待到账金额-、店铺余额+）；）
     * @param remark   流水备注
     * @return
     */
    @Override
    public void creditOrderCash(Long shopId, String orderId, BigDecimal amount, Integer cashType, Integer transSrc, String remark, Integer shopType) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        BigDecimal balance = amount;                  // 本期余额
        BigDecimal preBalance = BigDecimal.ZERO;      // 上期余额
        // 判断该店铺是否存在订单流水记录
        List<ShopOrderCashFlow> flowList = shopOrderCashFlowMapper.selectList(
                new EntityWrapper<ShopOrderCashFlow>()
                        .eq("shop_id", shopId)
                        .eq("cash_type", cashType)
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            preBalance = flowList.get(0).getAmount();
            balance = preBalance.add(amount);
        }
        ShopOrderCashFlow newFlow = new ShopOrderCashFlow();
        newFlow.setShopId(shopId);
        newFlow.setOrderId(orderId);
        newFlow.setCashType(cashType);
        newFlow.setTransSrc(transSrc);
        newFlow.setAmount(balance);
        newFlow.setCreditAmount(amount);
        newFlow.setDebitAmount(BigDecimal.ZERO);
        newFlow.setPreAmount(preBalance);
        newFlow.setRemark(remark);
        newFlow.setCreatedTime(new Date());
        newFlow.insert();
    }

    /**
     * 订单资金变动-
     *
     * @param shopId   店铺ID
     * @param orderId  订单ID
     * @param amount   变动金额
     * @param cashType 资金类型（1-待出账金额；2-待到账金额；）
     * @param transSrc 交易来源（1-用户已付款（待出账金额+）；
     *                 2-用户退款（待出账金额-）；
     *                 3-用户已收货=交易完成（待出账金额-、待到账金额+）；
     *                 4-用户售后退款（待到账金额-）；
     *                 5-订单已结算（待到账金额-、店铺余额+）；）
     * @param remark   流水备注
     * @return
     */
    @Override
    public void debitOrderCash(Long shopId, String orderId, BigDecimal amount, Integer cashType, Integer transSrc, String remark, Integer shopType) {
        // 变动金额小于等于0，不做处理
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            return;
        }
        // 判断查询该店铺是否存在余额流水记录
        List<ShopOrderCashFlow> flowList = shopOrderCashFlowMapper.selectList(
                new EntityWrapper<ShopOrderCashFlow>()
                        .eq("shop_id", shopId)
                        .eq("cash_type", cashType)
                        .orderBy("created_time", Boolean.FALSE));
        if (null != flowList && 0 < flowList.size()) {
            BigDecimal preBalance = flowList.get(0).getAmount();    // 上期余额
            BigDecimal balance = preBalance.subtract(amount);       // 本期余额
            ShopOrderCashFlow newFlow = new ShopOrderCashFlow();
            newFlow.setShopId(shopId);
            newFlow.setOrderId(orderId);
            newFlow.setCashType(cashType);
            newFlow.setTransSrc(transSrc);
            newFlow.setAmount(balance);
            newFlow.setCreditAmount(BigDecimal.ZERO);
            newFlow.setDebitAmount(amount);
            newFlow.setPreAmount(preBalance);
            newFlow.setRemark(remark);
            newFlow.setCreatedTime(new Date());
            newFlow.insert();
        }
    }
}
