package cn.ibdsr.web.modular.platform.shop.info.transfer;

import java.util.Date;

/**
 * 店铺列表VO对象
 *
 * @author XuZhipeng
 * @Date 2019-02-21 11:26:17
 */
public class ShopListVO {

    /**
     * 店铺ID
     */
    private Long id;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺类型名称（1-特产店铺；2-酒店店铺；）
     */
    private String shopTypeName;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 法人姓名
     */
    private String legalPerson;

    /**
     * 入驻时间
     */
    private Date createdTime;

    /**
     * 状态（1-未开通账号；2-正常营业；3-下架；）
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopTypeName() {
        return shopTypeName;
    }

    public void setShopTypeName(String shopTypeName) {
        this.shopTypeName = shopTypeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
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
