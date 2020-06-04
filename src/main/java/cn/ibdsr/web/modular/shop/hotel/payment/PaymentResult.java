package cn.ibdsr.web.modular.shop.hotel.payment;

import java.util.Map;

/**
 * *@Description 支付公共响应结果参数
 *
 * @Author xjc
 * @Date 2018-06-14 15:50:03
 */
public class PaymentResult {

    /**
     * 网关返回码
     */
    private String code;

    /**
     * 网关返回码描述
     */
    private String msg;

    /**
     * 业务返回码
     */
    private String subCode;

    /**
     * 业务返回码描述
     */
    private String subMsg;

    /**
     * 响应字符串
     */
    private String originalString;

    /****************************** 退款 start ********************************/
    /**
     * 交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 用户的登录id
     */
    private String buyerLogonId;

    /**
     * 本次退款是否发生了资金变化	Y
     */
    private String fundChange;

    /**
     * 退款总金额
     */
    private String refundFee;

    /**
     * 退款支付时间
     */
    private String gmtRefundPay;

    /**
     * 买家在支付宝的用户id
     */
    private String buyerUserId;
    /****************************** 退款 end **********************************/

    /****************************** 转账 start ********************************/
    /**
     * 支付宝转账单据号，查询失败不返回
     */
    private String orderId;

    /**
     * 结算单号
     */
    private String outBizNo;

    /**
     * 支付时间，格式为yyyy-MM-dd HH:mm:ss，转账失败不返回
     */
    private String payDate;

    /**
     * 预计到账时间，格式为yyyy-MM-dd HH:mm:ss，转账受理失败不返回。
     */
    private String arrivalTimeEnd;

    /**
     * 查询失败时，本参数为错误代码。查询成功不返回。对于退票订单，不返回该参数。
     */
    private String errorCode;

    /**
     * 查询到的订单状态为FAIL失败或REFUND退票时，返回具体的原因。
     */
    private String failReason;

    /**
     * 预计收费金额（元），转账到银行卡专用，数字格式，精确到小数点后2位，转账失败或转账受理失败不返回。
     */
    private String orderFee;

    /**
     * 转账单据状态。
     * SUCCESS：成功（配合"单笔转账到银行账户接口"产品使用时, 同一笔单据多次查询有可能从成功变成退票状态）；
     * FAIL：失败（具体失败原因请参见error_code以及fail_reason返回值）；
     * INIT：等待处理；
     * DEALING：处理中；
     * REFUND：退票（仅配合"单笔转账到银行账户接口"产品使用时会涉及, 具体退票原因请参见fail_reason返回值）；
     * UNKNOWN：状态未知。
     */
    private String status;
    /****************************** 转账 end **********************************/
    /****************************** 支付 start **********************************/
    /**
     * 预支付id
     */
    private String prepayId;

    /**
     * 支付响应参数
     */
    private Map<String, String> payMap;

    /****************************** 转账 end **********************************/
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public String getOriginalString() {
        return originalString;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getFundChange() {
        return fundChange;
    }

    public void setFundChange(String fundChange) {
        this.fundChange = fundChange;
    }

    public String getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(String refundFee) {
        this.refundFee = refundFee;
    }

    public String getGmtRefundPay() {
        return gmtRefundPay;
    }

    public void setGmtRefundPay(String gmtRefundPay) {
        this.gmtRefundPay = gmtRefundPay;
    }

    public String getBuyerUserId() {
        return buyerUserId;
    }

    public void setBuyerUserId(String buyerUserId) {
        this.buyerUserId = buyerUserId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOutBizNo() {
        return outBizNo;
    }

    public void setOutBizNo(String outBizNo) {
        this.outBizNo = outBizNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getArrivalTimeEnd() {
        return arrivalTimeEnd;
    }

    public void setArrivalTimeEnd(String arrivalTimeEnd) {
        this.arrivalTimeEnd = arrivalTimeEnd;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public String getOrderFee() {
        return orderFee;
    }

    public void setOrderFee(String orderFee) {
        this.orderFee = orderFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public Map<String, String> getPayMap() {
        return payMap;
    }

    public void setPayMap(Map<String, String> payMap) {
        this.payMap = payMap;
    }
}
