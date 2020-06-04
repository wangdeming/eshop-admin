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
 * 店铺订单结算表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-29
 */
@TableName("shop_order_settlement")
public class ShopOrderSettlement extends Model<ShopOrderSettlement> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private String orderId;
    /**
     * 订单金额（单位：分）
     */
	@TableField("order_amount")
	private BigDecimal orderAmount;
    /**
     * 退款金额（单位：分）
     */
	@TableField("refund_amount")
	private BigDecimal refundAmount;
    /**
     * 服务费（单位：分）
     */
	@TableField("service_fee")
	private BigDecimal serviceFee;
    /**
     * 服务费率（保留4位小数）
     */
	@TableField("service_rate")
	private BigDecimal serviceRate;
    /**
     * 结算金额 = （订单金额 - 退款金额）*（1 - 服务费率）（单位：分）
     */
	@TableField("settle_amount")
	private BigDecimal settleAmount;
    /**
     * 创建时间（发货时间）
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}

	public BigDecimal getSettleAmount() {
		return settleAmount;
	}

	public void setSettleAmount(BigDecimal settleAmount) {
		this.settleAmount = settleAmount;
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
		return "ShopOrderSettlement{" +
			"id=" + id +
			", orderId=" + orderId +
			", orderAmount=" + orderAmount +
			", refundAmount=" + refundAmount +
			", serviceFee=" + serviceFee +
			", serviceRate=" + serviceRate +
			", settleAmount=" + settleAmount +
			", createdTime=" + createdTime +
				", shopType=" + shopType +
			"}";
	}
}
