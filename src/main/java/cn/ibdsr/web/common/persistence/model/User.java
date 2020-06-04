package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 顾客（用户）信息表
 * </p>
 *
 * @author XXX
 * @since 2019-04-10
 */
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 昵称
     */
	private String nickname;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 手机号码
     */
	private String phone;
    /**
     * 生日
     */
	private Date birthday;
    /**
     * 性别（0-保密；1-男；2-女；）
     */
	private Integer sex;
    /**
     * 用户状态
     */
	private Integer status;
    /**
     * 地区：省ID
     */
	@TableField("province_id")
	private Long provinceId;
    /**
     * 地区：省
     */
	private String province;
    /**
     * 地区：市ID
     */
	@TableField("city_id")
	private Long cityId;
    /**
     * 地区：市
     */
	private String city;
    /**
     * 地区：区ID
     */
	@TableField("district_id")
	private Long districtId;
    /**
     * 地区：区
     */
	private String district;
    /**
     * 微信开放平台ID
     */
	@TableField("wx_open_id")
	private String wxOpenId;
    /**
     * 微信唯一ID
     */
	@TableField("wx_union_id")
	private String wxUnionId;
    /**
     * 国建
     */
	private String country;
    /**
     * 权限
     */
	private String privilege;
    /**
     * 微信登录时间
     */
	@TableField("wechat_login_datetime")
	private Date wechatLoginDatetime;
    /**
     * 创建人
     */
	@TableField("created_user")
	private Long createdUser;
    /**
     * 创建时间（发货时间）
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public String getWxUnionId() {
		return wxUnionId;
	}

	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public Date getWechatLoginDatetime() {
		return wechatLoginDatetime;
	}

	public void setWechatLoginDatetime(Date wechatLoginDatetime) {
		this.wechatLoginDatetime = wechatLoginDatetime;
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
		return "User{" +
			"id=" + id +
			", nickname=" + nickname +
			", avatar=" + avatar +
			", phone=" + phone +
			", birthday=" + birthday +
			", sex=" + sex +
			", status=" + status +
			", provinceId=" + provinceId +
			", province=" + province +
			", cityId=" + cityId +
			", city=" + city +
			", districtId=" + districtId +
			", district=" + district +
			", wxOpenId=" + wxOpenId +
			", wxUnionId=" + wxUnionId +
			", country=" + country +
			", privilege=" + privilege +
			", wechatLoginDatetime=" + wechatLoginDatetime +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
