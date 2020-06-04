/**
 * Copyright (c) 2015-2020, ShangRao Institute of Big Data co.,LTD and/or its
 * affiliates. All rights reserved.
 */
package cn.ibdsr.web.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 支付宝属性配置
 * @Version V1.0
 * @CreateDate 2018/6/27 17:00
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2018/2/27      xjc              类说明
 */
@Component
@ConfigurationProperties(prefix = "pay.wxpay")
public class WXpayProperties {

    /**
     * 应用ID
     */
    private String appid;

    private String mchid;

    private String key;

    private String certpath;

    /**
     * 回调URL
     */
    private String notifyurl;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCertpath() {
        return certpath;
    }

    public void setCertpath(String certpath) {
        this.certpath = certpath;
    }

    public String getNotifyurl() {
        return notifyurl;
    }

    public void setNotifyurl(String notifyurl) {
        this.notifyurl = notifyurl;
    }

}
