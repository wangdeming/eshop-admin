package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 酒店订单
 * </p>
 *
 * @author XXX
 * @since 2019-05-14
 */
@TableName("hotel_order")
public class HotelOrder extends Model<HotelOrder> {

    private static final long serialVersionUID = 1L;

	private String id;
    /**
     * 订单编号
     */
	@TableField("order_no")
	private String orderNo;
    /**
     * 酒店ID
     */
	@TableField("shop_id")
	private Long shopId;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Long userId;
    /**
     * 房间ID
     */
	@TableField("room_id")
	private Long roomId;
    /**
     * 房间数量
     */
	@TableField("room_number")
	private Integer roomNumber;
    /**
     * 单价，单位为分
     */
	private BigDecimal price;
    /**
     * 订单总金额，单位为分
     */
	@TableField("total_amount")
	private BigDecimal totalAmount;
    /**
     * 联系电话
     */
	private String mobile;
    /**
     * 入住时间
     */
	@TableField("check_in_date")
	private Date checkInDate;
    /**
     * 离店时间
     */
	@TableField("check_out_date")
	private Date checkOutDate;
    /**
     * 取消方式，0不可取消，1免费取消（当天14：00前可取消）
     */
	@TableField("can_cancel")
	private Integer canCancel;
    /**
     * 状态，1待付款，2待确认，3待使用，4已消费，5已取消
     */
	private Integer status;
    /**
	 * 支付时间
	 */
	@TableField("pay_datetime")
	private Date payDatetime;
	/**
	 * 商家确认时间
	 */
	@TableField("confirm_datetime")
	private Date confirmDatetime;
	/**
	 * 确认入住时间，用户到店办理入住手续时的入住时间
	 */
	@TableField("confirm_in_datetime")
	private Date confirmInDatetime;
	/**
	 * 取消原因
	 */
	@TableField("cancel_reason")
	private String cancelReason;
	/**
	 * 创建人
	 */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间
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


	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Integer getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(Integer canCancel) {
		this.canCancel = canCancel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPayDatetime() {
		return payDatetime;
	}

	public void setPayDatetime(Date payDatetime) {
		this.payDatetime = payDatetime;
	}

	public Date getConfirmDatetime() {
		return confirmDatetime;
	}

	public void setConfirmDatetime(Date confirmDatetime) {
		this.confirmDatetime = confirmDatetime;
	}

	public Date getConfirmInDatetime() {
		return confirmInDatetime;
	}

	public void setConfirmInDatetime(Date confirmInDatetime) {
		this.confirmInDatetime = confirmInDatetime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
		return "HotelOrder{" +
				"id=" + id +
				", orderNo=" + orderNo +
				", shopId=" + shopId +
				", userId=" + userId +
				", roomId=" + roomId +
				", roomNumber=" + roomNumber +
				", price=" + price +
				", totalAmount=" + totalAmount +
				", mobile=" + mobile +
				", checkInDate=" + checkInDate +
				", checkOutDate=" + checkOutDate +
				", canCancel=" + canCancel +
				", status=" + status +
				", payDatetime=" + payDatetime +
				", createdUser=" + createdUser +
				", createdTime=" + createdTime +
				", modifiedUser=" + modifiedUser +
				", modifiedTime=" + modifiedTime +
				", isDeleted=" + isDeleted +
				"}";
	}
}
