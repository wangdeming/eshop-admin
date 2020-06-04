package cn.ibdsr.web.modular.shop.hotel.payment;


import java.math.BigDecimal;

/**
 * *@Description 支付参数类
 *
 * @Author xjc
 * @Date 2018-06-14 15:50:03
 */
public class Payment {

    private String outTradeCode;

    private BigDecimal amount;

    private BigDecimal totalAmount;

    private String subject;

    private String ip;

    private String notifyUrl;

    private String openid;

    private String outRefundNo;

    /**
     * 支付方式 1为微信，2为支付宝，3为银联
     */
    private Integer payWay;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getOutTradeCode() {
        return outTradeCode;
    }

    public void setOutTradeCode(String outTradeCode) {
        this.outTradeCode = outTradeCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
