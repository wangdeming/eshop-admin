package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 店铺订单退款物流信息表（针对已发货订单）
 * </p>
 *
 * @author xjc
 * @since 2019-04-04
 */
@TableName("shop_order_refund_logistics")
public class ShopOrderRefundLogistics extends Model<ShopOrderRefundLogistics> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 退款订单ID
     */
    @TableField("refund_id")
    private Long refundId;
    /**
     * 商家收货人姓名
     */
    @TableField("consignee_name")
    private String consigneeName;
    /**
     * 商家收货人手机号
     */
    @TableField("consignee_phone")
    private String consigneePhone;
    /**
     * 商家收货地址：省
     */
    private String province;
    /**
     * 商家收货地址：市
     */
    private String city;
    /**
     * 商家收货地址：区
     */
    private String district;
    /**
     * 商家收货地址
     */
    private String address;
    /**
     * 物流状态（1-待发货；2-待收货；3-已收货；4-商家拒收；）
     */
    private Integer status;
    /**
     * 用户发货时间
     */
    @TableField("delivery_time")
    private Date deliveryTime;
    /**
     * 快递公司
     */
    @TableField("express_company")
    private String expressCompany;
    /**
     * 快递单号
     */
    @TableField("express_no")
    private String expressNo;
    /**
     * 快递说明
     */
    @TableField("express_remark")
    private String expressRemark;
    /**
     * 快递凭证图片（最多5张，多个用","隔开）
     */
    private String imgs;
    /**
     * 店铺收货备注
     */
    @TableField("receive_reason")
    private String receiveReason;
    /**
     * 店铺收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;
    /**
     * 拒收时间
     */
    @TableField("reject_time")
    private Date rejectTime;
    /**
     * 拒收原因
     */
    @TableField("reject_reason")
    private String rejectReason;
    /**
     * 创建人
     */
    @TableField("created_user")
    private Long createdUser;
    /**
     * 创建时间（申请退款时间）
     */
    @TableField("created_time")
    private Date createdTime;
    /**
     * 修改人
     */
    @TableField("modified_user")
    private Long modifiedUser;
    /**
     * 修改时间
     */
    @TableField("modified_time")
    private Date modifiedTime;
    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Integer isDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRefundId() {
        return refundId;
    }

    public void setRefundId(Long refundId) {
        this.refundId = refundId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getReceiveReason() {
        return receiveReason;
    }

    public void setReceiveReason(String receiveReason) {
        this.receiveReason = receiveReason;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(Date rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShopOrderRefundLogistics{" +
                "id=" + id +
                ", refundId=" + refundId +
                ", consigneeName=" + consigneeName +
                ", consigneePhone=" + consigneePhone +
                ", province=" + province +
                ", city=" + city +
                ", district=" + district +
                ", address=" + address +
                ", status=" + status +
                ", deliveryTime=" + deliveryTime +
                ", expressCompany=" + expressCompany +
                ", expressNo=" + expressNo +
                ", expressRemark=" + expressRemark +
                ", imgs=" + imgs +
                ", receiveTime=" + receiveTime +
                ", rejectTime=" + rejectTime +
                ", rejectReason=" + rejectReason +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
