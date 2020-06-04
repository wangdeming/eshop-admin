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
 * 资金提现记录表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-22
 */
@TableName("cash_withdrawal")
public class CashWithdrawal extends Model<CashWithdrawal> {

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
     * 提现方式（1-微信；2-支付宝；3-银行卡；）
     */
	@TableField("draw_way")
	private Integer drawWay;
    /**
     * 提现金额
     */
	private BigDecimal amount;
    /**
     * 账户姓名
     */
	@TableField("account_name")
	private String accountName;
    /**
     * 提现账号
     */
	@TableField("account_no")
	private String accountNo;
    /**
     * 提现状态（1-待审核；2-审核通过；3-确认打款；4-审核不通过；）
     */
	private Integer status;
    /**
     * 审核用户ID（平台用户）
     */
	@TableField("review_user_id")
	private Long reviewUserId;
    /**
     * 审核时间
     */
	@TableField("review_time")
	private Date reviewTime;
    /**
     * 审核说明
     */
	@TableField("review_remark")
	private String reviewRemark;
    /**
     * 确认打款时间
     */
	@TableField("confirm_time")
	private Date confirmTime;
    /**
     * 创建人
     */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间（提现申请时间）
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

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Integer getDrawWay() {
		return drawWay;
	}

	public void setDrawWay(Integer drawWay) {
		this.drawWay = drawWay;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getReviewUserId() {
		return reviewUserId;
	}

	public void setReviewUserId(Long reviewUserId) {
		this.reviewUserId = reviewUserId;
	}

	public Date getReviewTime() {
		return reviewTime;
	}

	public void setReviewTime(Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public String getReviewRemark() {
		return reviewRemark;
	}

	public void setReviewRemark(String reviewRemark) {
		this.reviewRemark = reviewRemark;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
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
		return "CashWithdrawal{" +
			"id=" + id +
			", shopId=" + shopId +
			", drawWay=" + drawWay +
			", amount=" + amount +
			", accountName=" + accountName +
			", accountNo=" + accountNo +
			", status=" + status +
			", reviewUserId=" + reviewUserId +
			", reviewTime=" + reviewTime +
			", reviewRemark=" + reviewRemark +
			", confirmTime=" + confirmTime +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
