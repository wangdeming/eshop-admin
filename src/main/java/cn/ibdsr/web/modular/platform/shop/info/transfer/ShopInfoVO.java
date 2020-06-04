package cn.ibdsr.web.modular.platform.shop.info.transfer;

import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺信息VO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class ShopInfoVO extends ShopAuthVO {

    /**
     * 店铺ID
     */
    private Long id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺前台名称
     */
    private String frontName;

    /**
     * 店铺类型（1-特产店铺；2-酒店店铺；）
     */
    private Integer type;

    /**
     * 店铺类型名称
     */
    private String shopTypeName;

    /**
     * 营业电话
     */
    private String officePhone;

    /**
     * 营业手机
     */
    private String officeTelphone;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 市ID
     */
    private Long cityId;

    /**
     * 区县ID
     */
    private Long districtId;

    /**
     * 街道ID
     */
    private Long streetId;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 维度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

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
     * 店铺图片路径集合
     */
    private List<String> imgList;

    /**
     * 省ID
     */
    private String provinceName;

    /**
     * 市ID
     */
    private String cityName;

    /**
     * 区县ID
     */
    private String districtName;

    /**
     * 街道ID
     */
    private String streetName;

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

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
