package cn.ibdsr.web.modular.platform.cash.transfer;

/**
 * @Description: 平台账户概览VO
 * @Version: V1.0
 * @CreateDate: 2019-04-22 14:22:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 14:22:11    XuZhipeng               类说明
 *
 */
public class PlatformAccountVO {

    /**
     * 平台余额
     */
    private String platformBalance;

    /**
     * 店铺总余额
     */
    private String shopBalance;

    /**
     * 服务费总额
     */
    private String serviceAmount;

    /**
     * 历史累计余额
     */
    private String totalBalance;

    /**
     * 历史累计提现（店铺）
     */
    private String withdrawalAmount;

    public String getPlatformBalance() {
        return platformBalance;
    }

    public void setPlatformBalance(String platformBalance) {
        this.platformBalance = platformBalance;
    }

    public String getShopBalance() {
        return shopBalance;
    }

    public void setShopBalance(String shopBalance) {
        this.shopBalance = shopBalance;
    }

    public String getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(String serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(String withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
