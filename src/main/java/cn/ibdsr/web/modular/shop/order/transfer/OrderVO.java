package cn.ibdsr.web.modular.shop.order.transfer;

import java.util.Date;
import java.util.List;


/**
 * @Description 订单管理-特产商城 订单信息VO
 * @Version V1.0
 * @CreateDate 2019-04-19 14:20:14
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019-04-19     ZhuJingrui            类说明
 */
public class OrderVO{

    /**
     * 店铺id
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 创建时间（下单时间）
     */
    private Date createdTime;

    /**
     * 下单顾客ID
     */
    private Long userId;

    /**
     * 状态（1-待付款；2-待发货；3-待收货；4-已收货（待评价）；5-已取消；6-退款中；7-交易关闭；8-已完成；）
     */
    private Integer status;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机号
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
     * 收货地址
     */
    private String address;

    /**
     * 订单价格（实际支付金额 = 商品总价 - 优惠券金额 + 快递运费，单位：分）
     */
    private String orderPrice;

    /**
     * 快递运费（单位：分）
     */
    private String expressFee;

    /**
     * 订单包含的商品列表
     */
    private List<OrderGoodsVO> goods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(String expressFee) {
        this.expressFee = expressFee;
    }

    public List<OrderGoodsVO> getGoods() {
        return goods;
    }

    public void setGoods(List<OrderGoodsVO> goods) {
        this.goods = goods;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
