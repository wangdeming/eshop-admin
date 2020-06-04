package cn.ibdsr.web.modular.platform.cash.transfer;

/**
 * @Description: 平台余额收支流水明细VO
 * @Version: V1.0
 * @CreateDate: 2019-04-22 16:53:11
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-22 16:53:11     XuZhipeng               类说明
 *
 */
public class PlatformBalanceFlowVO {

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 流水类型名称
     */
    private String flowType;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
     */
    private Integer transSrc;

    /**
     * 余额变化
     */
    private String amount;

    /**
     * 关联外部ID
     */
    private String tradeId;

    /**
     * 店铺ID
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺类型名称
     */
    private String shopType;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTransSrc() {
        return transSrc;
    }

    public void setTransSrc(Integer transSrc) {
        this.transSrc = transSrc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
