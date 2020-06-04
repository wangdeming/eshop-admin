package cn.ibdsr.web.modular.platform.shop.account.transfer;

/**
 * 店铺账号VO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-26 09:26:17
 */
public class ShopAccountVO {

    /**
     * 店铺账户ID
     */
    private Long id;

    /**
     * 店铺父账户ID
     */
    private Long pid;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺类型（1-特产店铺；2-酒店店铺；）
     */
    private String shopType;

    /**
     * 账户名
     */
    private String account;

    /**
     * 账号角色
     */
    private String role;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账号级别
     */
    private String level;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 创建时间
     */
    private String createdTime;

    /**
     * 状态（1-未激活；2-正常；3-冻结；）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
