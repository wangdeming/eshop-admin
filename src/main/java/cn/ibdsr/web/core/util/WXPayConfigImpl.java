package cn.ibdsr.web.core.util;

import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.config.properties.WXpayProperties;
import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class WXPayConfigImpl implements WXPayConfig {

    private WXpayProperties wXpayProperties = SpringContextHolder.getBean(WXpayProperties.class);

    private byte[] certData;

    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception {
        String certPath = wXpayProperties.getCertpath();
        URL url = WXPayConfig.class.getClassLoader().getResource(certPath);
        File file = new File(url.getFile());
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public static WXPayConfigImpl getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return wXpayProperties.getAppid();
    }

    public String getMchID() {
        return wXpayProperties.getMchid();
    }

    public String getKey() {
        return wXpayProperties.getKey();
    }

    public String getNotifyUrl() {
        return wXpayProperties.getNotifyurl();
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
