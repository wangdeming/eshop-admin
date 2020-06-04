package cn.ibdsr.web.modular.platform.shop.info.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.common.persistence.model.Area;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺信息DTO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class ShopInfoDTO extends BaseDTO {

    /**
     * 店铺ID
     */
    private Long id;

    /**
     * 店铺名称 唯一性
     */
    @Verfication(name = "店铺名称", notNull = true, maxlength = 50)
    private String name;

    /**
     * 店铺前台名称
     */
    @Verfication(name = "店铺前台名称", maxlength = 50)
    private String frontName;

    /**
     * 店铺类型（1-特产店铺；2-酒店店铺；）
     */
    @Verfication(name = "店铺类型", notNull = true)
    private Integer type;

    /**
     * 营业电话
     */
    @Verfication(name = "营业电话", regx = {CheckUtil.FIX_PHONE, "格式不正确"})
    private String officePhone;

    /**
     * 营业手机号码
     */
    @Verfication(name = "营业手机号码", regx = {CheckUtil.MOBILE_PHONE, "格式不正确"})
    private String officeTelphone;

    /**
     * 省ID：如江西省
     */
    @Verfication(name = "选取位置省份", notNull = true, forenign = Area.class)
    private Long provinceId;

    /**
     * 市ID：如上饶市
     */
    @Verfication(name = "选取位置城市", notNull = true, forenign = Area.class)
    private Long cityId;

    /**
     * 区县ID：如信州区
     */
    @Verfication(name = "选取位置县区", notNull = true, forenign = Area.class)
    private Long districtId;

    /**
     * 街道ID
     */
    @Verfication(name = "选取位置街道", notNull = true, forenign = Area.class)
    private Long streetId;

    /**
     * 店铺的详细地址
     */
    @Verfication(name = "详细地址", notNull = true, maxlength = 50)
    private String address;

    /**
     * 纬度
     */
    @Verfication(name = "纬度", notNull = true, min = -90, max = 90)
    private BigDecimal latitude;

    /**
     * 经度
     */
    @Verfication(name = "经度", notNull = true, min = -180, max = 180)
    private BigDecimal longitude;

    /**
     * 店铺简介
     */
    @Verfication(name = "店铺简介", maxlength = 54)
    private String intro;

    /**
     * 店铺封面
     */
    @Verfication(name = "店铺封面", maxlength = 255)
    private String cover;

    /**
     * 店铺LOGO
     */
    @Verfication(name = "店铺LOGO", maxlength = 255)
    private String logo;

    /**
     * 法人名字
     */
    @Verfication(name = "法人名字", notNull = true, maxlength = 20)
    private String legalPerson;

    /**
     * 性别
     */
    @Verfication(name = "性别", notNull = true)
    private Integer sex;

    /**
     * 法人手机号码
     */
    @Verfication(name = "法人手机号码", notNull = true, regx = {CheckUtil.MOBILE_PHONE, "格式不正确"})
    private String phone;

    /**
     * 法人身份证号码
     */
    @Verfication(name = "法人身份证号码", notNull = true, regx = {CheckUtil.ID_CARD, "格式不正确"})
    private String identityId;

    /**
     * 身份证头像面
     */
    @Verfication(name = "身份证头像面", notNull = true, maxlength = 255)
    private String identityPositive;

    /**
     * 身份证国徽面
     */
    @Verfication(name = "身份证国徽面", notNull = true, maxlength = 255)
    private String identityNegative;

    /**
     * 营业执照编号
     */
    @Verfication(name = "营业执照编号", notNull = true, regx = {CheckUtil.LISCENSE_ID, "格式不正确"})
    private String licenseId;

    /**
     * 营业执照图片
     */
    @Verfication(name = "营业执照图片", notNull = true, maxlength = 255)
    private String licenseImage;

    /**
     * 图片集合
     */
    private List<String> imgList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }
}
