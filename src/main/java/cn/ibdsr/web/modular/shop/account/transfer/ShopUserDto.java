package cn.ibdsr.web.modular.shop.account.transfer;

import cn.ibdsr.core.base.dto.BaseDTO;
import cn.ibdsr.core.check.CheckUtil;
import cn.ibdsr.core.check.Verfication;

import java.util.Date;

/**
 * <p>
 * 店铺用户表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-02-21
 */
public class ShopUserDto extends BaseDTO {

    private Long id;

    private Long shopId;

    @Verfication(name = "姓名", notNull = true, maxlength = 20, regx = {CheckUtil.NAME, "格式不正确"})
    private String name;

    private String avatar;

    @Verfication(name = "账号", notNull = true, regx = {cn.ibdsr.web.core.check.CheckUtil.ACCOUNT, "格式错误，正确格式为长度为2-15字符，允许包含字母、数字，不包含空格。"})
    private String account;

    private String accountType;

    @Verfication(name = "密码", notNull = true, regx = {CheckUtil.PASSWORD, "格式错误，格式为长度为6-16字符，必须包含字母、数字、符号中至少两种，不包含空格。"})
    private String password;

    private String salt;

    @Verfication(name = "性别", notNull = true)
    private Integer sex;

    private String phone;

    @Verfication(name = "角色", notNull = true)
    private String roleid;

    private Integer status;

    private Long pid;

    private Integer platformType;

    private Long createdUser;

    private Date createdTime;

    private Long modifiedUser;

    private Date modifiedTime;

    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
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

}
