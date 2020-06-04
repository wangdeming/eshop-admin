package cn.ibdsr.web.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author xjc
 * @since 2019-04-04
 */
public class Payment extends Model<Payment> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 支付业务类型（1-店铺商品；2-酒店；）
     */
    private Integer type;
    /**
     * 订单ID（多个用','隔开）
     */
    @TableField("order_ids")
    private String orderIds;
    /**
     * 微信支付平台商户号
     */
    @TableField("merchant_id")
    private String merchantId;
    /**
     * 统一交易订单号
     */
    @TableField("out_trade_no")
    private String outTradeNo;
    /**
     * 实际交易订单号（传给第三方支付平台，格式：H5或APP+out_trade_no）
     */
    @TableField("actual_out_trade_no")
    private String actualOutTradeNo;
    /**
     * 支付金额（单位：分）
     */
    @TableField("pay_amount")
    private BigDecimal payAmount;
    /**
     * 支付方式（1-微信；）
     */
    @TableField("pay_way")
    private Integer payWay;
    /**
     * 支付状态（1-已提交；2-支付成功；3-支付失败；）
     */
    private Integer status;
    /**
     * 第三方交易订单号
     */
    @TableField("transaction_id")
    private String transactionId;
    /**
     * 第三方支付总金额（单位：分）
     */
    @TableField("total_fee")
    private BigDecimal totalFee;
    /**
     * 支付完成时间
     */
    @TableField("complete_time")
    private Date completeTime;
    /**
     * 第三方接口返回的字符串
     */
    @TableField("original_string")
    private String originalString;
    /**
     * 创建人
     */
    @TableField("created_user")
    private Long createdUser;
    /**
     * 创建时间
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getActualOutTradeNo() {
        return actualOutTradeNo;
    }

    public void setActualOutTradeNo(String actualOutTradeNo) {
        this.actualOutTradeNo = actualOutTradeNo;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", type=" + type +
                ", orderIds=" + orderIds +
                ", merchantId=" + merchantId +
                ", outTradeNo=" + outTradeNo +
                ", actualOutTradeNo=" + actualOutTradeNo +
                ", payAmount=" + payAmount +
                ", payWay=" + payWay +
                ", status=" + status +
                ", transactionId=" + transactionId +
                ", totalFee=" + totalFee +
                ", completeTime=" + completeTime +
                ", originalString=" + originalString +
                ", createdUser=" + createdUser +
                ", createdTime=" + createdTime +
                ", modifiedUser=" + modifiedUser +
                ", modifiedTime=" + modifiedTime +
                "}";
    }
}
