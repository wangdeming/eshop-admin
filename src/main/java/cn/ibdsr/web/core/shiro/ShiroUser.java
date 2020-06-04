//package cn.ibdsr.web.core.shiro;
//
//import java.io.Serializable;
//
///**
// * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
// *
// * @author fengshuonan
// * @date 2016年12月5日 上午10:26:43
// */
//public class ShiroUser extends cn.ibdsr.core.shiro.ShiroUser implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 用户ID
//     */
//    //private Long id;
//
//    /**
//     * 账号
//     */
//    //private String account;
//
//    /**
//     * 姓名
//     */
//    //private String name;
//
//    /**
//     * 头像
//     */
//    private String avatar;
//
//    /**
//     * 店铺ID
//     */
//    private Long shopId;
//
//    /**
//     * 部门ID
//     */
//    private Long deptId;
//
//    /**
//     * 角色集
//     */
//    //private List<Long> roleList;
//
//    /**
//     * 角色名称集
//     */
//    //private List<String> roleNames;
//
//    /**
//     * 登录密码
//     */
//    private String password;
//
//    /**
//     * 密码盐
//     */
//    private String salt;
//
//    /**
//     * 平台类型（1-平台；2-店铺；）
//     */
//    private Integer platformType;
//
//    //public Long getId() {
//    //    return id;
//    //}
//    //
//    //public void setId(Long id) {
//    //    this.id = id;
//    //}
//    //
//    //public String getAccount() {
//    //    return account;
//    //}
//    //
//    //public void setAccount(String account) {
//    //    this.account = account;
//    //}
//    //
//    //public String getName() {
//    //    return name;
//    //}
//    //
//    //public void setName(String name) {
//    //    this.name = name;
//    //}
//
//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
//
//    public Long getShopId() {
//        return shopId;
//    }
//
//    public void setShopId(Long shopId) {
//        this.shopId = shopId;
//    }
//
//    public Long getDeptId() {
//        return deptId;
//    }
//
//    public void setDeptId(Long deptId) {
//        this.deptId = deptId;
//    }
//
//    //public List<Long> getRoleList() {
//    //    return roleList;
//    //}
//    //
//    //public void setRoleList(List<Long> roleList) {
//    //    this.roleList = roleList;
//    //}
//    //
//    //public List<String> getRoleNames() {
//    //    return roleNames;
//    //}
//    //
//    //public void setRoleNames(List<String> roleNames) {
//    //    this.roleNames = roleNames;
//    //}
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
//
//    public Integer getPlatformType() {
//        return platformType;
//    }
//
//    public void setPlatformType(Integer platformType) {
//        this.platformType = platformType;
//    }
//}
