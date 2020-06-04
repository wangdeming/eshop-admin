package cn.ibdsr.web.modular.shop.info.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;

public class MyShopInfoDTO extends BaseDTO {

    /**
     * 店铺前台名称
     */
    @Verfication(name = "店铺前台名称", notNull = true, maxlength = 50)
    private String frontName;

    /**
     * 营业电话
     */
    @Verfication(name = "营业电话", regx = {CheckUtil.FIX_PHONE, "格式不正确"})
    private String officePhone;

    /**
     * 营业手机号码
     */
    @Verfication(name = "营业手机号码", notNull = true, regx = {CheckUtil.MOBILE_PHONE, "格式不正确"})
    private String officeTelphone;

    /**
     * 店铺简介
     */
    @Verfication(name = "店铺简介", maxlength = 200)
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
}
