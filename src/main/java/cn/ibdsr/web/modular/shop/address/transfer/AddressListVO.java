package cn.ibdsr.web.modular.shop.address.transfer;
/**
 * @Description 店铺地址列表VO
 * @Version V1.0
 * @CreateDate 2019-04-19 14:01:57
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     Wujiayun           类说明
 */

public class AddressListVO {
    /**
     * 店铺退货地址ID
     */
    private Long id;
    /**
     * 收货人姓名
     */
    private String consigneeName;
    /**
     * 收货人手机号
     */
    private String consigneePhone;
    /**
     * 收货地址：省ID
     */
    private Long provinceId;
    /**
     * 收货地址：省
     */
    private String province;
    /**
     * 收货地址：市ID
     */
    private Long cityId;
    /**
     * 收货地址：市
     */
    private String city;
    /**
     * 收货地址：区ID
     */
    private Long districtId;
    /**
     * 收货地址：区
     */
    private String district;
    /**
     * 收货地址
     */
    private String address;

    /**
     * 是否默认（1-是；0-否；）
     */
    private Integer isDefault;
    /**
     * 地址类型（1-退货地址；）
     */
    private Integer type;

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
    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

}
