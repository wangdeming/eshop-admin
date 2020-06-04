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
 * 平台余额流水表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-19
 */
@TableName("platform_balance_flow")
public class PlatformBalanceFlow extends Model<PlatformBalanceFlow> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 交易来源（1-用户支付+；2-用户退款-；3-店铺提现成功-；）
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
     * 关联外部ID（订单ID-关联至shop_order表主键；提现ID-关联至cash_withdrawal表主键；）
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
		return "PlatformBalanceFlow{" +
			"id=" + id +
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
