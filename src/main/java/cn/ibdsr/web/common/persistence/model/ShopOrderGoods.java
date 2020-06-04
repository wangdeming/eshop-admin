package cn.ibdsr.web.common.persistence.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 店铺订单商品表
 * </p>
 *
 * @author XXX
 * @since 2019-04-08
 */
@TableName("shop_order_goods")
public class ShopOrderGoods extends Model<ShopOrderGoods> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 商品ID
     */
    @TableField("goods_id")
    private Long goodsId;
    /**
     * 规格商品ID
     */
    @TableField("sku_id")
    private Long skuId;
    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;
    /**
     * 规格商品图片
     */
    @TableField("goods_img")
    private String goodsImg;
    /**
     * 规格信息（例：2KG,微辣）
     */
    @TableField("goods_specs")
    private String goodsSpecs;
    /**
     * 商品出售单价（单位：分）
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;
    /**
     * 购买数量
     */
    private Integer nums;
    /**
     * 商品总价（单价 * 数量，单位：分）
     */
    private BigDecimal price;
    /**
     * 发货状态（1-待发货；2-已发货；3-已收货；）
     */
    @TableField("delivery_status")
    private Integer deliveryStatus;
    /**
     * 发货时间
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
     * 收货时间
     */
    @TableField("receive_time")
    private Date receiveTime;
    /**
     * 售后状态（1-无售后；2-退款中；3-已退款；4-退款失败（商家拒绝）；）
     */
    @TableField("service_status")
    private Integer serviceStatus;
    /**
     * 创建人
     */
    @TableField("created_user")
    private Long createdUser;
    /**
     * 创建时间（下单时间）
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(String goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
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
        return "ShopOrderGoods{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", skuId=" + skuId +
                ", goodsName=" + goodsName +
                ", goodsImg=" + goodsImg +
                ", goodsSpecs=" + goodsSpecs +
                ", unitPrice=" + unitPrice +
                ", nums=" + nums +
                ", price=" + price +
                ", deliveryStatus=" + deliveryStatus +
                ", deliveryTime=" + deliveryTime +
                ", expressCompany=" + expressCompany +
                ", expressNo=" + expressNo +
                ", receiveTime=" + receiveTime +
                ", serviceStatus=" + serviceStatus +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
