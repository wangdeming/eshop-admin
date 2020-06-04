package cn.ibdsr.web.modular.shop.money.service.impl;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.web.common.constant.state.IsDeleted;
import cn.ibdsr.web.common.constant.state.cash.CashWithdrawalStatus;
import cn.ibdsr.web.common.constant.state.cash.ShopBalanceFlowTransSrc;
import cn.ibdsr.web.common.constant.state.cash.ShopOrderCashFlowCashType;
import cn.ibdsr.web.common.constant.state.shop.ShopType;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.CashWithdrawalMapper;
import cn.ibdsr.web.common.persistence.dao.HotelOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ProfitDistributionMapper;
import cn.ibdsr.web.common.persistence.dao.ShopBalanceFlowMapper;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderCashFlowMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderMapper;
import cn.ibdsr.web.common.persistence.dao.ShopOrderSettlementMapper;
import cn.ibdsr.web.common.persistence.model.CashWithdrawal;
import cn.ibdsr.web.common.persistence.model.HotelOrder;
import cn.ibdsr.web.common.persistence.model.ProfitDistribution;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopBalanceFlow;
import cn.ibdsr.web.common.persistence.model.ShopOrder;
import cn.ibdsr.web.common.persistence.model.ShopOrderCashFlow;
import cn.ibdsr.web.common.persistence.model.ShopOrderSettlement;
import cn.ibdsr.web.modular.platform.cash.service.ICashChangeService;
import cn.ibdsr.web.modular.shop.money.service.MoneyService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @Description: 资产管理
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/19 14:51
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/19 xujincai init
 */
@Service
public class MoneyServiceImpl implements MoneyService {

    @Autowired
    private ShopBalanceFlowMapper shopBalanceFlowMapper;

    @Autowired
    private CashWithdrawalMapper cashWithdrawalMapper;

    @Autowired
    private ShopOrderCashFlowMapper shopOrderCashFlowMapper;

    @Autowired
    private ShopOrderSettlementMapper shopOrderSettlementMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private HotelOrderMapper hotelOrderMapper;

    @Autowired
    private ICashChangeService cashChangeService;

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private ProfitDistributionMapper profitDistributionMapper;

    /**
     * 店铺账户概览
     *
     * @return SuccessDataTip
     */
    @Override
    public SuccessDataTip moneyDetail() {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        JSONObject returnJson = new JSONObject();

        //店铺可用余额，从店铺订单资金流水表获取店铺最新一条流水记录的余额即为店铺可用余额
        Wrapper<ShopBalanceFlow> shopBalanceFlowWrapper = new EntityWrapper<>();
        shopBalanceFlowWrapper.eq("shop_id", shopId).orderBy("created_time", Boolean.FALSE);
        List<ShopBalanceFlow> shopBalanceFlowList = shopBalanceFlowMapper.selectList(shopBalanceFlowWrapper);
        if (shopBalanceFlowList.size() > 0) {
            returnJson.put("availableBalance", shopBalanceFlowList.get(0).getBalance().divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            //历史累计余额，店铺余额流水表店铺的贷记金额累计
            BigDecimal historyBalanceSum = new BigDecimal(0);
            for (ShopBalanceFlow shopBalanceFlow : shopBalanceFlowList) {
                BigDecimal CreditAmount = shopBalanceFlow.getCreditAmount() == null ? new BigDecimal(0) : shopBalanceFlow.getCreditAmount();
                historyBalanceSum = historyBalanceSum.add(CreditAmount);
            }
            returnJson.put("historyBalanceSum", historyBalanceSum.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
        } else {
            returnJson.put("availableBalance", "0.00");
            returnJson.put("historyBalanceSum", "0.00");
        }

        //店铺不可用余额，统计资金提现记录表中商家的待审核金额
        Wrapper<CashWithdrawal> cashWithdrawalWrapper = new EntityWrapper<>();
        cashWithdrawalWrapper.eq("shop_id", shopId).eq("status", CashWithdrawalStatus.WAIT_AUDIT.getCode()).eq("is_deleted", IsDeleted.NORMAL.getCode());
        List<CashWithdrawal> cashWithdrawalList = cashWithdrawalMapper.selectList(cashWithdrawalWrapper);
        if (cashWithdrawalList.size() > 0) {
            BigDecimal unavailableBalance = new BigDecimal(0);
            for (CashWithdrawal cashWithdrawal : cashWithdrawalList) {
                unavailableBalance = unavailableBalance.add(cashWithdrawal.getAmount());
            }
            returnJson.put("unavailableBalance", unavailableBalance.divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
        } else {
            returnJson.put("unavailableBalance", "0.00");
        }

        //待到账金额，店铺订单资金流水表商家资金类型为待到账金额最新一条记录的当前金额
        //待出账金额，店铺订单资金流水表商家资金类型为待出账金额最新一条记录的当前金额
        Wrapper<ShopOrderCashFlow> shopOrderCashFlowWrapper = new EntityWrapper<>();
        shopOrderCashFlowWrapper.eq("shop_id", shopId).eq("cash_type", ShopOrderCashFlowCashType.CREDIT_AMOUNT.getCode()).orderBy("created_time", Boolean.FALSE);
        List<ShopOrderCashFlow> shopOrderCashFlowList = shopOrderCashFlowMapper.selectList(shopOrderCashFlowWrapper);
        if (shopOrderCashFlowList.size() > 0) {
            returnJson.put("ddzAmount", shopOrderCashFlowList.get(0).getAmount().divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
        } else {
            returnJson.put("ddzAmount", "0.00");
        }
        shopOrderCashFlowWrapper = new EntityWrapper<>();
        shopOrderCashFlowWrapper.eq("shop_id", shopId).eq("cash_type", ShopOrderCashFlowCashType.DEBIT_AMOUNT.getCode()).orderBy("created_time", Boolean.FALSE);
        shopOrderCashFlowList = shopOrderCashFlowMapper.selectList(shopOrderCashFlowWrapper);
        if (shopOrderCashFlowList.size() > 0) {
            returnJson.put("dczAmount", shopOrderCashFlowList.get(0).getAmount().divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
        } else {
            returnJson.put("dczAmount", "0.00");
        }

        //服务费率
        ProfitDistribution t = new ProfitDistribution();
        t.setShopId(shopId);
        ProfitDistribution profitDistribution = profitDistributionMapper.selectOne(t);
        String serviceRate = "0.00%";
        if (profitDistribution != null) {
            if (profitDistribution.getEffectiveTime() == null || new Date().before(profitDistribution.getEffectiveTime())) {
                serviceRate = profitDistribution.getServiceRate().multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).setScale(2).toPlainString() + "%";
            } else {
                serviceRate = profitDistribution.getChangeServiceRate().multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).setScale(2).toPlainString() + "%";
            }
        }
        returnJson.put("serviceRate", serviceRate);

        return new SuccessDataTip(returnJson);
    }

    /**
     * 店铺余额收支明细
     *
     * @param offset    offset
     * @param limit     limit
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return SuccessDataTip
     */
    @Override
    public JSONObject balanceList(int offset, int limit, LocalDate beginDate, LocalDate endDate) {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        RowBounds rowBounds = new RowBounds(offset, limit);
        Wrapper<ShopBalanceFlow> shopBalanceFlowWrapper = new EntityWrapper<>();
        shopBalanceFlowWrapper.setSqlSelect("created_time", "trans_src", "debit_amount", "credit_amount", "trade_id").eq("shop_id", shopId).orderBy("created_time", Boolean.FALSE);
        if (beginDate != null) {
            shopBalanceFlowWrapper.ge("created_time", beginDate);
        }
        if (endDate != null) {
            shopBalanceFlowWrapper.lt("created_time", endDate.plusDays(1));
        }
        Integer total = shopBalanceFlowMapper.selectCount(shopBalanceFlowWrapper);
        List<ShopBalanceFlow> shopBalanceFlowList = shopBalanceFlowMapper.selectPage(rowBounds, shopBalanceFlowWrapper);
        List<JSONObject> rows = JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(shopBalanceFlowList, "yyyy-MM-dd HH:mm:ss"), JSONObject.class);
        for (JSONObject row : rows) {
            row.put("transSrcName", ShopBalanceFlowTransSrc.valueOf(row.getIntValue("transSrc")));
            if (row.getIntValue("transSrc") == ShopBalanceFlowTransSrc.ORDER_ADD.getCode()) {
                row.put("balance", "+" + row.getBigDecimal("creditAmount").divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
            if (row.getIntValue("transSrc") == ShopBalanceFlowTransSrc.WITHDRAWAL_SUB.getCode()) {
                row.put("balance", "-" + row.getBigDecimal("debitAmount").divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
            if (row.getIntValue("transSrc") == ShopBalanceFlowTransSrc.WITHDRAWAL_ADD.getCode()) {
                row.put("balance", "+" + row.getBigDecimal("creditAmount").divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            }
            ShopOrderSettlement shopOrderSettlement = shopOrderSettlementMapper.selectById(row.getString("tradeId"));
            if (shopOrderSettlement != null) {
                row.put("serviceFee", shopOrderSettlement.getServiceFee().divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
                Shop shop = shopMapper.selectById(shopId);
                row.put("shopType", shop.getType());
                if (shop.getType() == ShopType.STORE.getCode()) {
                    ShopOrder shopOrder = shopOrderMapper.selectById(shopOrderSettlement.getOrderId());
                    if (shopOrder != null) {
                        row.put("orderId", shopOrder.getId());
                        row.put("orderNo", shopOrder.getOrderNo());
                    }
                }
                if (shop.getType() == ShopType.HOTEL.getCode()) {
                    HotelOrder hotelOrder = hotelOrderMapper.selectById(shopOrderSettlement.getOrderId());
                    if (hotelOrder != null) {
                        row.put("orderId", hotelOrder.getId());
                        row.put("orderNo", hotelOrder.getOrderNo());
                    }
                }
            }
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("total", total);
        returnJson.put("rows", rows);
        return returnJson;
    }

    /**
     * 获取商家最近一次提现的账户姓名和提现账户
     *
     * @return SuccessDataTip
     */
    @Override
    public SuccessDataTip lastWithdrawalInfo() {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        Wrapper<CashWithdrawal> cashWithdrawalWrapper = new EntityWrapper<>();
        cashWithdrawalWrapper.setSqlSelect("draw_way", "account_name", "account_no").eq("shop_id", shopId).eq("is_deleted", IsDeleted.NORMAL.getCode()).orderBy("created_time", Boolean.FALSE);
        List<CashWithdrawal> cashWithdrawalList = cashWithdrawalMapper.selectList(cashWithdrawalWrapper);
        JSONObject returnJson = new JSONObject();
        if (cashWithdrawalList.size() > 0) {
            returnJson = JSONObject.parseObject(JSONObject.toJSONString(cashWithdrawalList.get(0)));
        }
        //店铺可用余额，从店铺订单资金流水表获取店铺最新一条流水记录的余额即为店铺可用余额
        Wrapper<ShopBalanceFlow> shopBalanceFlowWrapper = new EntityWrapper<>();
        shopBalanceFlowWrapper.eq("shop_id", shopId).orderBy("created_time", Boolean.FALSE);
        List<ShopBalanceFlow> shopBalanceFlowList = shopBalanceFlowMapper.selectList(shopBalanceFlowWrapper);
        if (shopBalanceFlowList.size() > 0) {
            returnJson.put("availableBalance", shopBalanceFlowList.get(0).getBalance().divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
        } else {
            returnJson.put("availableBalance", "0");
        }
        return new SuccessDataTip(returnJson);
    }

    /**
     * 申请提现
     *
     * @param amount      金额，传入单位为元，后续转换为分存到数据库
     * @param drawWay     提现方式
     * @param accountName 账户姓名
     * @param accountNo   提现账户
     * @return SuccessMesTip
     */
    @Override
    @Transactional
    public SuccessMesTip withdrawal(BigDecimal amount, int drawWay, String accountName, String accountNo) {
        if (amount == null) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_AMOUNT_NOT_BLANK);
        }
        if (StringUtils.isBlank(accountName)) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_ACCOUNTNAME_NOT_BLANK);
        }
        if (StringUtils.isBlank(accountNo)) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_ACCOUNTNO_NOT_BLANK);
        }
        if (amount.compareTo(new BigDecimal(100)) < 0) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_AMOUNT_100);
        }
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        Wrapper<ShopBalanceFlow> shopBalanceFlowWrapper = new EntityWrapper<>();
        shopBalanceFlowWrapper.eq("shop_id", shopId).orderBy("created_time", Boolean.FALSE);
        List<ShopBalanceFlow> shopBalanceFlowList = shopBalanceFlowMapper.selectList(shopBalanceFlowWrapper);
        if (shopBalanceFlowList.size() == 0 || amount.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)).compareTo(shopBalanceFlowList.get(0).getBalance()) > 0) {
            throw new BussinessException(BizExceptionEnum.WITHDRAWAL_AMOUNT_CAN_NOT_GREATER_THAN_BALANCE);
        }

        CashWithdrawal cashWithdrawal = new CashWithdrawal();
        cashWithdrawal.setShopId(shopId);
        cashWithdrawal.setDrawWay(drawWay);
        cashWithdrawal.setAmount(amount.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)));
        cashWithdrawal.setAccountName(accountName);
        cashWithdrawal.setAccountNo(accountNo);
        cashWithdrawal.setStatus(CashWithdrawalStatus.WAIT_AUDIT.getCode());
        cashWithdrawal.setCreatedUser(shopId);
        cashWithdrawal.setCreatedTime(new Date());
        cashWithdrawal.insert();

        //增加资金变动记录，店铺申请提现：店铺余额-（shop_balance_flow）
        cashChangeService.debitShopBalance(shopId, amount.multiply(BigDecimal.TEN.multiply(BigDecimal.TEN)), ShopBalanceFlowTransSrc.WITHDRAWAL_SUB.getCode(), String.valueOf(cashWithdrawal.getId()), null);

        return new SuccessMesTip("提现申请成功");
    }


    /**
     * 提现记录，分页
     *
     * @param offset    offset
     * @param limit     limit
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return JSONObject
     */
    @Override
    public JSONObject withdrawalList(int offset, int limit, LocalDate beginDate, LocalDate endDate) {
        Long shopId = ((ShopData) ShiroKit.getUser().getData()).getShopId();
        RowBounds rowBounds = new RowBounds(offset, limit);
        Wrapper<CashWithdrawal> cashWithdrawalWrapper = new EntityWrapper<>();
        cashWithdrawalWrapper.setSqlSelect("id", "amount", "status", "created_time").eq("shop_id", shopId).eq("is_deleted", IsDeleted.NORMAL.getCode()).orderBy("created_time", Boolean.FALSE);
        if (beginDate != null) {
            cashWithdrawalWrapper.ge("created_time", beginDate);
        }
        if (endDate != null) {
            cashWithdrawalWrapper.lt("created_time", endDate.plusDays(1));
        }
        Integer total = cashWithdrawalMapper.selectCount(cashWithdrawalWrapper);
        List<CashWithdrawal> cashWithdrawalList = cashWithdrawalMapper.selectPage(rowBounds, cashWithdrawalWrapper);
        List<JSONObject> rows = JSONObject.parseArray(JSONObject.toJSONStringWithDateFormat(cashWithdrawalList, "yyyy-MM-dd HH:mm:ss"), JSONObject.class);
        for (JSONObject row : rows) {
            row.put("amount", row.getBigDecimal("amount").divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            CashWithdrawalStatus[] cashWithdrawalStatuses = CashWithdrawalStatus.values();
            for (CashWithdrawalStatus status : cashWithdrawalStatuses) {
                if (row.getIntValue("status") == status.getCode()) {
                    row.put("statusName", status.getMessage());
                }
            }
        }
        JSONObject returnJson = new JSONObject();
        returnJson.put("total", total);
        returnJson.put("rows", rows);
        return returnJson;
    }

    /**
     * 提现详情
     *
     * @param id 提现详情主键ID
     * @return SuccessDataTip
     */
    @Override
    public SuccessDataTip withdrawalDetail(Long id) {
        CashWithdrawal cashWithdrawal = cashWithdrawalMapper.selectById(id);
        JSONObject returnJson = JSONObject.parseObject(JSONObject.toJSONStringWithDateFormat(cashWithdrawal, "yyyy-MM-dd HH:mm:ss"));
        if (returnJson != null) {
            returnJson.put("amount", returnJson.getBigDecimal("amount").divide(BigDecimal.TEN.multiply(BigDecimal.TEN), 2, BigDecimal.ROUND_HALF_DOWN).toPlainString());
            for (CashWithdrawalStatus status : CashWithdrawalStatus.values()) {
                if (returnJson.getIntValue("status") == status.getCode()) {
                    returnJson.put("statusName", status.getMessage());
                }
            }
        }
        return new SuccessDataTip(returnJson);
    }

}
