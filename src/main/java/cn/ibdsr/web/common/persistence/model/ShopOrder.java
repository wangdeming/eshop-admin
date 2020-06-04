package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 店铺订单信息表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-24
 */
@TableName("shop_order")
public class ShopOrder extends Model<ShopOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 店铺ID
     */
	@TableField("shop_id")
	private Long shopId;
    /**
     * 下单顾客ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 状态（1-待付款；2-待发货；3-待收货；4-已收货；5-已取消；6-退款中；7-交易关闭；8-已完成（订单已结算）；）
     */
	private Integer status;
    /**
     * 申请退款前订单状态
     */
	@TableField("pre_status")
	private Integer preStatus;
    /**
     * 订单价格（实际支付金额 = 商品总价 - 优惠券金额 + 快递运费，单位：分）
     */
	@TableField("order_price")
	private BigDecimal orderPrice;
    /**
     * 优惠券金额（单位：分）
     */
	@TableField("coupon_amount")
	private BigDecimal couponAmount;
    /**
     * 商品总价格（单位：分）
     */
	@TableField("goods_price")
	private BigDecimal goodsPrice;
    /**
     * 快递运费（单位：分）
     */
	@TableField("express_fee")
	private BigDecimal expressFee;
    /**
     * 配送方式（1-快递发货；）
     */
	@TableField("delivery_type")
	private Integer deliveryType;
    /**
     * 收货人姓名
     */
	@TableField("consignee_name")
	private String consigneeName;
    /**
     * 收货人手机号
     */
	@TableField("consignee_phone")
	private String consigneePhone;
    /**
     * 收货地址：省
     */
	private String province;
    /**
     * 收货地址：市
     */
	private String city;
    /**
     * 收货地址：区
     */
	private String district;
    /**
     * 收货地址
     */
	private String address;
    /**
     * 支付时间
     */
	@TableField("payment_time")
	private Date paymentTime;
    /**
     * 取消（关闭）时间
     */
	@TableField("cancel_time")
	private Date cancelTime;
    /**
     * 订单取消备注
     */
	@TableField("cancel_remark")
	private String cancelRemark;
    /**
     * 是否已评价（0-否；1-是；）
     */
	@TableField("is_evaluated")
	private Integer isEvaluated;
    /**
     * 创建人
     */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间（下单时间）
     */
	@TableField("created_time")
	private Date createdTime;
    /**
     * 修改人
     */
	@TableField("modified_user")
	private Long modifiedUser;
    /**
     * 修改时间
     */
	@TableField("modified_time")
	private Date modifiedTime;
    /**
     * 是否删除
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(Integer preStatus) {
		this.preStatus = preStatus;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public BigDecimal getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(BigDecimal expressFee) {
		this.expressFee = expressFee;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public Integer getIsEvaluated() {
		return isEvaluated;
	}

	public void setIsEvaluated(Integer isEvaluated) {
		this.isEvaluated = isEvaluated;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "ShopOrder{" +
			"id=" + id +
			", orderNo=" + orderNo +
			", shopId=" + shopId +
			", userId=" + userId +
			", status=" + status +
			", preStatus=" + preStatus +
			", orderPrice=" + orderPrice +
			", couponAmount=" + couponAmount +
			", goodsPrice=" + goodsPrice +
			", expressFee=" + expressFee +
			", deliveryType=" + deliveryType +
			", consigneeName=" + consigneeName +
			", consigneePhone=" + consigneePhone +
			", province=" + province +
			", city=" + city +
			", district=" + district +
			", address=" + address +
			", paymentTime=" + paymentTime +
			", cancelTime=" + cancelTime +
			", cancelRemark=" + cancelRemark +
			", isEvaluated=" + isEvaluated +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
