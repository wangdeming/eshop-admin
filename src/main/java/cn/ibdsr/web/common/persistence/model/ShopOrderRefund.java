package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 店铺订单退款记录表
 * </p>
 *
 * @author XuZhipeng
 * @since 2019-04-15
 */
@TableName("shop_order_refund")
public class ShopOrderRefund extends Model<ShopOrderRefund> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    /**
     * 订单商品ID
     */
    @TableField("order_goods_id")
    private Long orderGoodsId;
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 店铺ID
     */
    @TableField("shop_id")
    private Long shopId;
    /**
     * 退款单号
     */
    @TableField("refund_order_no")
    private String refundOrderNo;
    /**
     * 退款类型（1-仅退款；2-退货退款；）
     */
    private Integer type;
    /**
     * 售后/退款状态（1-待审核；2-审核通过（type=1-直接退款；type=2-等待用户发货；）；3-审核不通过；4-退款成功；5-退款失败；6-用户撤销；7-自动撤销（未填写退货物流）；）
     */
    private Integer status;
    /**
     * 退款原因ID（字典表num）
     */
    @TableField("reason_id")
    private Integer reasonId;
    /**
     * 其他原因
     */
    @TableField("other_reason")
    private String otherReason;
    /**
     * 商品数量
     */
    @TableField("goods_num")
    private Integer goodsNum;
    /**
     * 退款总金额（单位：分）
     */
    @TableField("refund_amount")
    private BigDecimal refundAmount;
    /**
     * 快递运费（单位：分）
     */
    @TableField("express_fee")
    private BigDecimal expressFee;
    /**
     * 联系手机
     */
    private String phone;
    /**
     * 备注，退款说明
     */
    @TableField("refund_remark")
    private String refundRemark;
    /**
     * 凭证图片（最多5张，多个用","隔开）
     */
    private String imgs;
    /**
     * 审核人ID
     */
    @TableField("review_user_id")
    private Long reviewUserId;
    /**
     * 审核时间
     */
    @TableField("review_time")
    private Date reviewTime;
    /**
     * 审核备注
     */
    @TableField("review_remark")
    private String reviewRemark;
    /**
     * 撤销时间
     */
    @TableField("revoke_time")
    private Date revokeTime;
    /**
     * 撤销原因
     */
    @TableField("revoke_reason")
    private String revokeReason;
    /**
     * 第三方退款单号
     */
    @TableField("original_refund_id")
    private String originalRefundId;
    /**
     * 第三方接口返回的字符串
     */
    @TableField("original_string")
    private String originalString;
    /**
     * 预计到账时间
     */
    @TableField("expected_time")
    private Date expectedTime;
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

    public Long getOrderGoodsId() {
        return orderGoodsId;
    }

    public void setOrderGoodsId(Long orderGoodsId) {
        this.orderGoodsId = orderGoodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(BigDecimal expressFee) {
        this.expressFee = expressFee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRefundRemark() {
        return refundRemark;
    }

    public void setRefundRemark(String refundRemark) {
        this.refundRemark = refundRemark;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public Long getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(Long reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewRemark() {
        return reviewRemark;
    }

    public void setReviewRemark(String reviewRemark) {
        this.reviewRemark = reviewRemark;
    }

    public Date getRevokeTime() {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) {
        this.revokeTime = revokeTime;
    }

    public String getRevokeReason() {
        return revokeReason;
    }

    public void setRevokeReason(String revokeReason) {
        this.revokeReason = revokeReason;
    }

    public String getOriginalRefundId() {
        return originalRefundId;
    }

    public void setOriginalRefundId(String originalRefundId) {
        this.originalRefundId = originalRefundId;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(Date expectedTime) {
        this.expectedTime = expectedTime;
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
        return "ShopOrderRefund{" +
                "id=" + id +
                ", orderGoodsId=" + orderGoodsId +
                ", orderId=" + orderId +
                ", shopId=" + shopId +
                ", refundOrderNo=" + refundOrderNo +
                ", type=" + type +
                ", status=" + status +
                ", reasonId=" + reasonId +
                ", otherReason=" + otherReason +
                ", goodsNum=" + goodsNum +
                ", refundAmount=" + refundAmount +
                ", expressFee=" + expressFee +
                ", phone=" + phone +
                ", refundRemark=" + refundRemark +
                ", imgs=" + imgs +
                ", reviewUserId=" + reviewUserId +
                ", reviewTime=" + reviewTime +
                ", reviewRemark=" + reviewRemark +
                ", revokeTime=" + revokeTime +
                ", revokeReason=" + revokeReason +
                ", originalRefundId=" + originalRefundId +
                ", originalString=" + originalString +
                ", expectedTime=" + expectedTime +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                ", isDeleted=" + isDeleted +
                "}";
    }
}
