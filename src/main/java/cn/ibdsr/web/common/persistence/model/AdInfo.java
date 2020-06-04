package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 广告信息表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-01
 */
@TableName("ad_info")
public class AdInfo extends Model<AdInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 广告位置ID
     */
	@TableField("position_id")
	private Long positionId;
    /**
     * 关联类型（1-商品；2-店铺；3-URL；4-无关联；）
     */
	private Integer type;
    /**
     * 关联值（根据type类型认定）
     */
	@TableField("relation_val")
	private String relationVal;
    /**
     * 图片
     */
	private String img;
    /**
     * 排序号
     */
	private Integer sequence;
    /**
     * 状态（1-创建；2-已发布；3-下线；）
     */
	private Integer status;
    /**
     * 发布时间（生效时间）
     */
	@TableField("publish_time")
	private Date publishTime;
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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRelationVal() {
		return relationVal;
	}

	public void setRelationVal(String relationVal) {
		this.relationVal = relationVal;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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
		return "AdInfo{" +
			"id=" + id +
			", positionId=" + positionId +
			", type=" + type +
			", relationVal=" + relationVal +
			", img=" + img +
			", sequence=" + sequence +
			", status=" + status +
			", publishTime=" + publishTime +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
