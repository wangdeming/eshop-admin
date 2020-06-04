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
 * 店铺订单资金流水表（待到账/待出账金额）
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-19
 */
@TableName("shop_order_cash_flow")
public class ShopOrderCashFlow extends Model<ShopOrderCashFlow> {

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
     * 订单ID
     */
	@TableField("order_id")
	private String orderId;
    /**
     * 资金类型（1-待出账金额；2-待到账金额；）
     */
	@TableField("cash_type")
	private Integer cashType;
    /**
     * 交易来源（1-用户已付款（待出账金额+）；2-用户退款（待出账金额-）；3-用户已收货=交易完成（待出账金额-、待到账金额+）；4-用户售后退款（待到账金额-）；5-订单已结算（待到账金额-、店铺余额+）；）
     */
	@TableField("trans_src")
	private Integer transSrc;
    /**
     * 当前金额（单位：分）
     */
	private BigDecimal amount;
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
     * 上期金额（单位：分）
     */
	@TableField("pre_amount")
	private BigDecimal preAmount;
    /**
     * 流水备注说明
     */
	private String remark;
    /**
     * 创建时间
     */
	@TableField("created_time")
	private Date createdTime;
	/**
	 * 店铺类型（1-特产店铺；2-酒店店铺；）
	 */
	@TableField("shop_type")
	private Integer shopType;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getCashType() {
		return cashType;
	}

	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

	public Integer getTransSrc() {
		return transSrc;
	}

	public void setTransSrc(Integer transSrc) {
		this.transSrc = transSrc;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	public BigDecimal getPreAmount() {
		return preAmount;
	}

	public void setPreAmount(BigDecimal preAmount) {
		this.preAmount = preAmount;
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

	public Integer getShopType() {
		return shopType;
	}

	public void setShopType(Integer shopType) {
		this.shopType = shopType;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ShopOrderCashFlow{" +
			"id=" + id +
			", shopId=" + shopId +
			", orderId=" + orderId +
			", cashType=" + cashType +
			", transSrc=" + transSrc +
			", amount=" + amount +
			", creditAmount=" + creditAmount +
			", debitAmount=" + debitAmount +
			", preAmount=" + preAmount +
			", remark=" + remark +
			", createdTime=" + createdTime +
				", shopType=" + shopType +
			"}";
	}
}
