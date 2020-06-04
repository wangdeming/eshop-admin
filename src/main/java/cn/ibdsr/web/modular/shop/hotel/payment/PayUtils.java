/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.modular.shop.hotel.payment;


import cn.ibdsr.core.util.MD5Util;
import com.github.wxpay.sdk.WXPayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

;

/**
 * @Description 支付Utils
 * @Version V1.0
 * @CreateDate 2018/8/22 8:29
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/8/22       xjc                类说明
 */
public class PayUtils {
    private static Logger log = LoggerFactory.getLogger(PayUtils.class);

    private static String TRADE_FINISHED = "TRADE_FINISHED";
    private static String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 获取支付宝回调返回的请求参数，并进行处理
     */
    public static Map<String, String> getAliPayRequestParam(Map requestParams) {
        Map<String, String> params = new HashMap<String, String>();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /*    *//**校验支付宝支付校验是否通过*//*
    public static boolean isAliPayVerify(Map<String,String> params,HttpServletRequest request){
        boolean verify = false;
        try {
            AlipayProperties alipayProperties = ConstantFactory.me().getAlipayProperties();
            *//**切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看*//*
            verify = AlipaySignature.rsaCheckV1(params,alipayProperties.getAliPublicKey(), alipayProperties.getCharset(),alipayProperties.getSignType());
        }catch (Exception e) {
            e.printStackTrace();
        }
        *//**交易状态 *//*
        String tradeStatus = request.getParameter("trade_status");
        boolean flag = verify &&(TRADE_FINISHED.equals(tradeStatus) || TRADE_SUCCESS.equals(tradeStatus));
        return flag;
    }*/

    /**
     * 获取微信回调返回的请求参数，并进行处理
     */
    public static Map<String, String> getWXRequestParam(HttpServletRequest request) {
        //读取参数
        InputStream inputStream = null;
        StringBuffer sb = new StringBuffer();
        BufferedReader in = null;
        try {
            inputStream = request.getInputStream();
            String s;
            in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 转换成map
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notifyMap;
    }

    /**
     * 微信退款回调参数解密
     *
     * @param notifyMap
     * @return
     */
    public static Map<String, String> getWxRefundNotifyCertDecrypt(Map<String, String> notifyMap) throws Exception {
        byte[] b = new BASE64Decoder().decodeBuffer(notifyMap.get("req_info"));
        WXPayConfigImpl config = WXPayConfigImpl.getInstance();
        SecretKeySpec key = new SecretKeySpec(MD5Util.encrypt(config.getKey()).toLowerCase().getBytes(), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        String str = new String(cipher.doFinal(b), "utf-8");
        log.info("解密后结果----------------------------" + str);
        Map<String, String> paramMap = WXPayUtil.xmlToMap(str);  // 转换成map
        return paramMap;
    }

}