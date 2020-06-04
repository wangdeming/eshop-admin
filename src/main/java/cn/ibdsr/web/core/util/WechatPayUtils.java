package cn.ibdsr.web.core.util;

import com.github.wxpay.sdk.WXPay;

import java.util.Map;

/**
 * @Description: 微信支付工具类
 * @Version: V1.0
 * @CreateUser: xujincai
 * @CreateDate: 2019/4/4 10:53
 * <p>
 * Date Author Description
 * ------------------------------------------------------
 * 2019/4/4 xujincai init
 */
public class WechatPayUtils {

    private WXPay wxpay;

    private WXPayConfigImpl config;

    public WechatPayUtils() throws Exception {
        config = WXPayConfigImpl.getInstance();
        wxpay = new WXPay(config);
    }

    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        return wxpay.refund(reqData);
    }
}
