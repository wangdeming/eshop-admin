package cn.ibdsr.web.modular.platform.cash.transfer;

/**
 * @Description: 今日实时数据VO
 * @Version: V1.0
 * @CreateDate: 2019-04-22 14:22:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:22:11    XuZhipeng               类说明
 *
 */
public class TodayDataVO {

    /**
     * 变动余额
     */
    private String changeBalance;

    /**
     * 订单成交额
     */
    private String orderAmount;

    /**
     * 成交笔数
     */
    private Integer orderNum;

    /**
     * 退款金额
     */
    private String refundAmount;

    /**
     * 退款笔数
     */
    private Integer refundNum;

    /**
     * 提现金额
     */
    private String withdrawalAmount;

    /**
     * 提现笔数
     */
    private Integer withdrawalNum;

    public String getChangeBalance() {
        return changeBalance;
    }

    public void setChangeBalance(String changeBalance) {
        this.changeBalance = changeBalance;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Integer getRefundNum() {
        return refundNum;
    }

    public void setRefundNum(Integer refundNum) {
        this.refundNum = refundNum;
    }

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Integer getWithdrawalNum() {
        return withdrawalNum;
    }

    public void setWithdrawalNum(Integer withdrawalNum) {
        this.withdrawalNum = withdrawalNum;
    }
}
