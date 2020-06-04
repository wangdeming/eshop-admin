package cn.ibdsr.web.modular.shop.order.transfer;

import java.util.List;

/**
 * @Description 退款物流信息VO
 * @Version V1.0
 * @CreateDate 2019-04-25 18:29:38
 *
 * Date                     Author               Description
 * ----------------------------------------------------------
 * 2019-04-25 18:29:38     ZhuJingrui             类说明
 */
public class RefundLogisticsVO {

    /**
     * 退款物流Id
     */
    Long refundLogisticsId;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人联系电话
     */
    private String consigneePhone;

    /**
     * 收货地址：省
     */
    private String province;

    /**
     * 收货地址：市
     */
    private String city;

    /**
     * 收货地址：区
     */
    private String district;

    /**
     * 收货详细地址
     */
    private String address;

    /**
     * 退货物流状态（1-待发货；2-待收货；3-已收货；4-商家拒收；）
     */
    private Integer status;

    /**
     * 发货时间
     */
    private String deliveryTime;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 快递物流说明
     */
    private String expressRemark;

    /**
     * 快递凭证图片集合
     */
    private List<String> imgList;

    /**
     * 收货时间
     */
    private String receiveTime;

    /**
     * 拒收时间
     */
    private String rejectTime;

    /**
     * 拒收原因
     */
    private String rejectReason;

    /**
     * 店铺收货备注
     */
    private String receiveReason;

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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
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

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Long getRefundLogisticsId() {
        return refundLogisticsId;
    }

    public void setRefundLogisticsId(Long refundLogisticsId) {
        this.refundLogisticsId = refundLogisticsId;
    }

    public String getReceiveReason() {
        return receiveReason;
    }

    public void setReceiveReason(String receiveReason) {
        this.receiveReason = receiveReason;
    }
}
