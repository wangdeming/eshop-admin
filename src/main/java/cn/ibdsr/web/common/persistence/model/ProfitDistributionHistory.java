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
 * 收益分配历史记录表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-22
 */
@TableName("profit_distribution_history")
public class ProfitDistributionHistory extends Model<ProfitDistributionHistory> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 收益配置ID（关联至profit_distribution主键）
     */
	@TableField("distribution_id")
	private Long distributionId;
    /**
     * 变更前服务费率
     */
	@TableField("before_service_rate")
	private BigDecimal beforeServiceRate;
    /**
     * 变更后服务费率
     */
	@TableField("after_service_rate")
	private BigDecimal afterServiceRate;
    /**
     * 生效时间
     */
	@TableField("effective_time")
	private Date effectiveTime;
    /**
     * 创建人姓名（平台管理员）
     */
	@TableField("created_user_name")
	private String createdUserName;
    /**
     * 创建人账号（平台管理员）
     */
	@TableField("created_user_account")
	private String createdUserAccount;
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

	public Long getDistributionId() {
		return distributionId;
	}

	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}

	public BigDecimal getBeforeServiceRate() {
		return beforeServiceRate;
	}

	public void setBeforeServiceRate(BigDecimal beforeServiceRate) {
		this.beforeServiceRate = beforeServiceRate;
	}

	public BigDecimal getAfterServiceRate() {
		return afterServiceRate;
	}

	public void setAfterServiceRate(BigDecimal afterServiceRate) {
		this.afterServiceRate = afterServiceRate;
	}

	public Date getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(Date effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getCreatedUserName() {
		return createdUserName;
	}

	public void setCreatedUserName(String createdUserName) {
		this.createdUserName = createdUserName;
	}

	public String getCreatedUserAccount() {
		return createdUserAccount;
	}

	public void setCreatedUserAccount(String createdUserAccount) {
		this.createdUserAccount = createdUserAccount;
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
		return "ProfitDistributionHistory{" +
			"id=" + id +
			", distributionId=" + distributionId +
			", beforeServiceRate=" + beforeServiceRate +
			", afterServiceRate=" + afterServiceRate +
			", effectiveTime=" + effectiveTime +
			", createdUserName=" + createdUserName +
			", createdUserAccount=" + createdUserAccount +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
