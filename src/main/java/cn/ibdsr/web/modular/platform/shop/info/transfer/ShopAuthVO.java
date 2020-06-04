package cn.ibdsr.web.modular.platform.shop.info.transfer;

/**
 * 店铺法人认证信息VO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class ShopAuthVO {

    /**
     * 法人姓名
     */
    private String legalPerson;

    /**
     * 性别（1-男；2-女；）
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String identityId;

    /**
     * 身份证正面
     */
    private String identityPositive;

    /**
     * 身份证反面
     */
    private String identityNegative;

    /**
     * 营业执照编号
     */
    private String licenseId;

    /**
     * 营业执照图片
     */
    private String licenseImage;

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
}
