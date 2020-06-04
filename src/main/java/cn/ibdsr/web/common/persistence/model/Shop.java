package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 店铺信息表
 * </p>
 *
 * @author XXX
 * @since 2019-05-08
 */
public class Shop extends Model<Shop> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 店铺类型（1-特产店铺；2-酒店店铺；）
     */
	private Integer type;
    /**
     * 店铺名称（唯一）
     */
	private String name;
    /**
     * 店铺前台名称
     */
	@TableField("front_name")
	private String frontName;
    /**
     * 营业电话
     */
	@TableField("office_phone")
	private String officePhone;
    /**
     * 营业手机号码
     */
	@TableField("office_telphone")
	private String officeTelphone;
    /**
     * 店铺状态（1-未开通账号；2-下架；3-正常营业；）
     */
	private Integer status;
    /**
     * 省ID（如江西省）
     */
	@TableField("province_id")
	private Long provinceId;
    /**
     * 市ID（如上饶市）
     */
	@TableField("city_id")
	private Long cityId;
    /**
     * 区县ID（如信州区）
     */
	@TableField("district_id")
	private Long districtId;
    /**
     * 街道ID
     */
	@TableField("street_id")
	private Long streetId;
    /**
     * 详细地址
     */
	private String address;
    /**
     * 纬度
     */
	private BigDecimal latitude;
    /**
     * 经度
     */
	private BigDecimal longitude;
    /**
     * 到灵山景区的距离，以米为单位
     */
    @TableField("distance_lingshan")
    private Integer distanceLingshan;
    /**
     * 店铺简介
     */
    private String intro;
    /**
     * 店铺封面
     */
	private String cover;
    /**
     * 店铺logo
     */
	private String logo;
    /**
     * 营业开始时间
     */
	@TableField("begin_time")
	private Date beginTime;
    /**
     * 营业结束时间
     */
	@TableField("end_time")
	private Date endTime;
    /**
     * 法人名字
     */
	@TableField("legal_person")
	private String legalPerson;
    /**
     * 性别
     */
	private Integer sex;
    /**
     * 法人手机号码
     */
	private String phone;
    /**
     * 法人身份证ID
     */
	@TableField("identity_id")
	private String identityId;
    /**
     * 身份证头像面
     */
	@TableField("identity_positive")
	private String identityPositive;
    /**
     * 身份证国徽面
     */
	@TableField("identity_negative")
	private String identityNegative;
    /**
     * 营业执照ID
     */
	@TableField("license_id")
	private String licenseId;
    /**
     * 营业执照图片
     */
	@TableField("license_image")
	private String licenseImage;
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
     * 是否删除（0-删除；1-未删除；）
     */
	@TableField("is_deleted")
	private Integer isDeleted;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFrontName() {
		return frontName;
	}

	public void setFrontName(String frontName) {
		this.frontName = frontName;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getOfficeTelphone() {
		return officeTelphone;
	}

	public void setOfficeTelphone(String officeTelphone) {
		this.officeTelphone = officeTelphone;
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getStreetId() {
		return streetId;
	}

	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

    public Integer getDistanceLingshan() {
        return distanceLingshan;
    }

    public void setDistanceLingshan(Integer distanceLingshan) {
        this.distanceLingshan = distanceLingshan;
    }

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getIdentityPositive() {
		return identityPositive;
	}

	public void setIdentityPositive(String identityPositive) {
		this.identityPositive = identityPositive;
	}

	public String getIdentityNegative() {
		return identityNegative;
	}

	public void setIdentityNegative(String identityNegative) {
		this.identityNegative = identityNegative;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getLicenseImage() {
		return licenseImage;
	}

	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
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
		return "Shop{" +
			"id=" + id +
			", type=" + type +
			", name=" + name +
			", frontName=" + frontName +
			", officePhone=" + officePhone +
			", officeTelphone=" + officeTelphone +
			", status=" + status +
			", provinceId=" + provinceId +
			", cityId=" + cityId +
			", districtId=" + districtId +
			", streetId=" + streetId +
			", address=" + address +
			", latitude=" + latitude +
			", longitude=" + longitude +
                ", distanceLingshan=" + distanceLingshan +
			", intro=" + intro +
			", cover=" + cover +
			", logo=" + logo +
			", beginTime=" + beginTime +
			", endTime=" + endTime +
			", legalPerson=" + legalPerson +
			", sex=" + sex +
			", phone=" + phone +
			", identityId=" + identityId +
			", identityPositive=" + identityPositive +
			", identityNegative=" + identityNegative +
			", licenseId=" + licenseId +
			", licenseImage=" + licenseImage +
			", createdUser=" + createdUser +
			", createdTime=" + createdTime +
			", modifiedUser=" + modifiedUser +
			", modifiedTime=" + modifiedTime +
			", isDeleted=" + isDeleted +
			"}";
	}
}
