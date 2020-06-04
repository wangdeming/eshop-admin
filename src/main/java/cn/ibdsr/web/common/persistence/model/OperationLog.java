package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-07-24
 */
@TableName("operation_log")
public class OperationLog extends Model<OperationLog> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	/**
	 * 日志类型
	 */
	private String logtype;
	/**
	 * 日志名称
	 */
	private String logname;
	/**
	 * 类名称
	 */
	private String classname;
	/**
	 * 方法名称
	 */
	private String method;
	/**
	 * 是否成功
	 */
	private String succeed;
	/**
	 * 备注
	 */
	private String message;
	/**
	 * 管理员姓名
	 */
	@TableField("user_name")
	private String userName;
	/**
	 * 管理员账号
	 */
	@TableField("user_account")
	private String userAccount;
	/**
	 * 用户id
	 */
	@TableField("created_user")
	private Long createdUser;
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

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSucceed() {
		return succeed;
	}

	public void setSucceed(String succeed) {
		this.succeed = succeed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "OperationLog{" +
				"id=" + id +
				", logtype=" + logtype +
				", logname=" + logname +
				", classname=" + classname +
				", method=" + method +
				", succeed=" + succeed +
				", message=" + message +
				", userName=" + userName +
				", userAccount=" + userAccount +
				", createdUser=" + createdUser +
				", createdTime=" + createdTime +
				"}";
	}
}
