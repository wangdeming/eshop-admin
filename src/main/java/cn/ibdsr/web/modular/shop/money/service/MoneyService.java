package cn.ibdsr.web.modular.shop.money.service;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDate;

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
public interface MoneyService {
    SuccessDataTip moneyDetail();

    JSONObject balanceList(int offset, int limit, LocalDate beginDate, LocalDate endDate);

    SuccessDataTip lastWithdrawalInfo();

    SuccessMesTip withdrawal(BigDecimal amount, int drawWay, String accountName, String accountNo);

    JSONObject withdrawalList(int offset, int limit, LocalDate beginDate, LocalDate endDate);

    SuccessDataTip withdrawalDetail(Long id);
}
