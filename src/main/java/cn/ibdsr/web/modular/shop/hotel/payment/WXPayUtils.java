package cn.ibdsr.web.modular.shop.hotel.payment;


import cn.ibdsr.web.common.constant.factory.ConstantFactory;
import cn.ibdsr.web.common.constant.state.hotel.PaymentReturnCode;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xjc
 * DateTime: 2019/1/19 9:29
 */
public class WXPayUtils {

    private static Logger log = LoggerFactory.getLogger(WXPayUtils.class);

    private WXPay wxpay;

    private WXPayConfigImpl config;

    public WXPayUtils() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
    }

    /**
     * 支付统一下单
     */
    public PaymentResult payment(Payment payment) {
        log.info("进入微信支付统一下单接口---------------------");
        PaymentResult paymentResult = new PaymentResult();
        //支付参数
        HashMap<String, String> data = new HashMap<>();
        data.put("openid", payment.getOpenid());
        data.put("body", payment.getSubject());
        data.put("out_trade_no", payment.getOutTradeCode());
        data.put("total_fee", payment.getAmount().toPlainString());
        data.put("spbill_create_ip", payment.getIp());
        data.put("notify_url", ConstantFactory.me().getUrls().getBathurl() + payment.getNotifyUrl());
        data.put("trade_type", "JSAPI");
        data.put("sign_type", "MD5");
        log.info("请求参数：" + JSONObject.toJSONString(data));
        try {
            Map<String, String> resultMap = wxpay.unifiedOrder(data);
            log.info("响应参数：" + JSONObject.toJSONString(resultMap));

            Map<String, String> payMap = new HashMap<>();
            payMap.put("appId", resultMap.get("appid"));
            payMap.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
            payMap.put("nonceStr", resultMap.get("nonce_str"));
            payMap.put("package", "prepay_id=" + resultMap.get("prepay_id"));
            payMap.put("signType", WXPayConstants.SignType.MD5.name());
            payMap.put("paySign", WXPayUtil.generateSignature(payMap, config.getKey()));
            paymentResult.setPayMap(payMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentResult;
    }

    /**
     * 查询订单
     *
     * @param mch_id       商户号
     * @param out_trade_no 商户订单号
     * @return Map<String, String>
     * @throws Exception Exception
     */
    public Map<String, String> orderquery(String mch_id, String out_trade_no) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("appid", ConstantFactory.me().getWXpayProperties().getAppid());
        data.put("mch_id", mch_id);
        data.put("out_trade_no", out_trade_no);
        data.put("sign_type", WXPayConstants.SignType.MD5.name());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("sign", WXPayUtil.generateSignature(data, ConstantFactory.me().getWXpayProperties().getKey(), WXPayConstants.SignType.MD5));
        return wxpay.orderQuery(data);
    }

    /**
     * 退款
     */
    public PaymentResult refund(Payment payment) {
        log.info("进入微信退款接口---------------------");
        PaymentResult paymentResult = new PaymentResult();
        HashMap<String, String> data = new HashMap<>();
        data.put("out_trade_no", payment.getOutTradeCode());
        data.put("out_refund_no", payment.getOutRefundNo());
        data.put("total_fee", payment.getTotalAmount().stripTrailingZeros().toPlainString());
        data.put("refund_fee", payment.getAmount().stripTrailingZeros().toPlainString());
        data.put("refund_fee_type", "CNY");
        data.put("op_user_id", config.getMchID());
        data.put("notify_url", ConstantFactory.me().getUrls().getBathurl() + payment.getNotifyUrl());
        log.info("请求参数：" + JSONObject.toJSONString(data));
        try {
            Map<String, String> resultMap = wxpay.refund(data);
            log.info("响应参数：" + JSONObject.toJSONString(resultMap));
            if (resultMap != null && "SUCCESS".equals(resultMap.get("return_code"))) {
                if ("SUCCESS".equals(resultMap.get("result_code"))) {
                    paymentResult.setCode(PaymentReturnCode.SUCCESS.getCode());
                    paymentResult.setOriginalString(JSONObject.toJSONString(resultMap));
                    paymentResult.setOutBizNo(resultMap.get("refund_id"));
                } else {
                    paymentResult.setCode(PaymentReturnCode.ERROR.getCode());
                    paymentResult.setMsg(resultMap.get("return_msg"));
                    paymentResult.setSubCode(resultMap.get("err_code"));
                    paymentResult.setSubMsg(resultMap.get("err_code_des"));
                }
            } else {
                paymentResult.setCode(PaymentReturnCode.FAIL.getCode());
                paymentResult.setSubMsg(resultMap.get("return_msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paymentResult;
    }

}


