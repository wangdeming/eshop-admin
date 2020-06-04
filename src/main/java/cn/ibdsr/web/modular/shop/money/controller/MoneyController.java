package cn.ibdsr.web.modular.shop.money.controller;

import cn.ibdsr.core.base.tips.SuccessDataTip;
import cn.ibdsr.core.base.tips.SuccessMesTip;
import cn.ibdsr.web.modular.shop.money.service.MoneyService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Description: 资产管理
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/19 14:50
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/19 xujincai init
 */
@Controller
@RequestMapping(value = "shop/money")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    /**
     * 跳转资金详情页面
     *
     * @return String
     */
    @RequestMapping(value = "tomoneydetail")
    public String toMoneyDetail() {
        return "/shop/money/moneyDetail.html";
    }

    /**
     * 跳转余额收支明细页面
     *
     * @return String
     */
    @RequestMapping(value = "tobalancelist")
    public String toBalanceList() {
        return "/shop/money/balanceList.html";
    }

    /**
     * 跳转发起提现页面
     *
     * @return String
     */
    @RequestMapping(value = "towithdrawal")
    public String toWithdrawal() {
        return "/shop/money/withdrawal.html";
    }

    /**
     * 跳转提现记录页面
     *
     * @return String
     */
    @RequestMapping(value = "towithdrawalrecord")
    public String toWithdrawalRecord() {
        return "/shop/money/withdrawalRecord.html";
    }

    /**
     * 跳转提现详情页面
     *
     * @return String
     */
    @RequestMapping(value = "towithdrawaldetail")
    public String toWithdrawalDetail() {
        return "/shop/money/withdrawalDetail.html";
    }

    /**
     * 店铺账户概览
     *
     * @return SuccessDataTip
     */
    @RequestMapping(value = "moneydetail")
    @ResponseBody
    public SuccessDataTip moneyDetail() {
        return moneyService.moneyDetail();
    }

    /**
     * 店铺余额收支明细
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return SuccessDataTip
     */
    @RequestMapping(value = "balancelist")
    @ResponseBody
    public JSONObject balanceList(@RequestParam int offset, @RequestParam int limit, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return moneyService.balanceList(offset, limit, beginDate, endDate);
    }

    /**
     * 获取商家最近一次提现的账户姓名和提现账户
     *
     * @return SuccessDataTip
     */
    @RequestMapping(value = "lastwithdrawalinfo")
    @ResponseBody
    public SuccessDataTip lastWithdrawalInfo() {
        return moneyService.lastWithdrawalInfo();
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
    @RequestMapping(value = "withdrawal")
    @ResponseBody
    public SuccessMesTip withdrawal(@RequestParam BigDecimal amount, @RequestParam int drawWay, @RequestParam String accountName, @RequestParam String accountNo) {
        return moneyService.withdrawal(amount, drawWay, accountName, accountNo);
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
    @RequestMapping(value = "withdrawallist")
    @ResponseBody
    public JSONObject withdrawalList(@RequestParam int offset, @RequestParam int limit, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return moneyService.withdrawalList(offset, limit, beginDate, endDate);
    }

    /**
     * 提现详情
     *
     * @param id 提现详情主键ID
     * @return SuccessDataTip
     */
    @RequestMapping(value = "withdrawaldetail")
    @ResponseBody
    public SuccessDataTip withdrawalDetail(@RequestParam Long id) {
        return moneyService.withdrawalDetail(id);
    }

}
