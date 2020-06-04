package cn.ibdsr.web.modular.shop.address.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;
import cn.ibdsr.web.common.persistence.model.Area;

/**
 * @Description 收货地址 店铺收货地址DTO
 * @Version V1.0
 * @CreateDate 2019-03-18 13:57:38
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/03/18     Wujiayun            类说明
 */
public class ShopAddressDTO extends BaseDTO {
    /**
     * 收货地址ID
     */
    private Long id;
    /**
     * 收货人姓名
     */
    @Verfication(name = "收货人姓名", notNull = true, maxlength = 15)
    private String consigneeName;
    /**
     * 收货人手机号
     */
    @Verfication(name = "收货人手机号", notNull = true, regx = {CheckUtil.MOBILE_PHONE, "格式不正确"})
    private String consigneePhone;
    /**
     * 收货地址：省
     */
    @Verfication(name = "收货地址省份", notNull = true, forenign = Area.class)
    private Long provinceId;
    /**
     * 收货地址：市
     */
    @Verfication(name = "收货地址城市", notNull = true, forenign = Area.class)
    private Long cityId;
    /**
     * 收货地址：区
     */
    @Verfication(name = "收货地址县区", notNull = true, forenign = Area.class)
    private Long districtId;
    /**
     * 收货地址
     */
    @Verfication(name = "收货地址", notNull = true, minlength = 5, maxlength = 200)
    private String address;
    /**
     * 是否默认（1-是；0-否；）
     */
    @Verfication(name = "是否默认", notNull = true)
    private Integer isDefault;
    /**
     * 地址类型（1-退货地址；）
     */
    @Verfication(name = "地址类型", notNull = true)
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
