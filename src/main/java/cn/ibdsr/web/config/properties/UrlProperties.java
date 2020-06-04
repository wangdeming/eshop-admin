package cn.ibdsr.web.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "guns.url-prefix")
public class UrlProperties {

    private String messageurl;

    private String bathurl;

    public String getBathurl() {
        return bathurl;
    }

    public void setBathurl(String bathurl) {
        this.bathurl = bathurl;
    }

    public String getMessageurl() {
        return messageurl;
    }

    public void setMessageurl(String messageurl) {
        this.messageurl = messageurl;
    }
}