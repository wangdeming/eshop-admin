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
 * fastdfs文件上传日志
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-02-25
 */
@TableName("file_upload_log")
public class FileUploadLog extends Model<FileUploadLog> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("file_suffix")
	private String fileSuffix;
	@TableField("file_type")
	private String fileType;
	@TableField("file_name")
	private String fileName;
	@TableField("file_size")
	private Long fileSize;
	private String account;
	@TableField("account_id")
	private Long accountId;
	private String path;
	@TableField("platform_type")
	private Integer platformType;
	private Date createtime;
	private String description;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getPlatformType() {
		return platformType;
	}

	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "FileUploadLog{" +
			"id=" + id +
			", fileSuffix=" + fileSuffix +
			", fileType=" + fileType +
			", fileName=" + fileName +
			", fileSize=" + fileSize +
			", account=" + account +
			", accountId=" + accountId +
			", path=" + path +
			", platformType=" + platformType +
			", createtime=" + createtime +
			", description=" + description +
			"}";
	}
}
