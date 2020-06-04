package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 店铺余额流水表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-19
 */
@TableName("shop_balance_flow")
public class ShopBalanceFlow extends Model<ShopBalanceFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 店铺ID
     */
	@TableField("shop_id")
	private Long shopId;
    /**
     * 交易来源（1-订单结算+；2-提现-；3-提现不通过返还+；）
     */
	@TableField("trans_src")
	private Integer transSrc;
    /**
     * 当前余额（单位：分）
     */
	private BigDecimal balance;
    /**
     * 贷记金额+（单位：分）
     */
	@TableField("credit_amount")
	private BigDecimal creditAmount;
    /**
     * 借记金额-（单位：分）
     */
	@TableField("debit_amount")
	private BigDecimal debitAmount;
    /**
     * 上期余额（单位：分）
     */
	@TableField("pre_balance")
	private BigDecimal preBalance;
    /**
     * 关联外部ID（订单结算ID-关联至shop_order_settlement表主键；提现ID-关联至cash_withdrawal表主键；）
     */
	@TableField("trade_id")
    private String tradeId;
    /**
     * 余额流水备注说明
     */
	private String remark;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Date createdTime;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getTransSrc() {
		return transSrc;
	}

	public void setTransSrc(Integer transSrc) {
		this.transSrc = transSrc;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = debitAmount;
	}

	public BigDecimal getPreBalance() {
		return preBalance;
	}

	public void setPreBalance(BigDecimal preBalance) {
		this.preBalance = preBalance;
	}

    public String getTradeId() {
		return tradeId;
	}

    public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ShopBalanceFlow{" +
			"id=" + id +
			", shopId=" + shopId +
			", transSrc=" + transSrc +
			", balance=" + balance +
			", creditAmount=" + creditAmount +
			", debitAmount=" + debitAmount +
			", preBalance=" + preBalance +
			", tradeId=" + tradeId +
			", remark=" + remark +
			", createdTime=" + createdTime +
			"}";
	}
}
