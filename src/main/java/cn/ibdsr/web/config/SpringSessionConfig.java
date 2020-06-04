package cn.ibdsr.web.config;

import cn.ibdsr.core.log.LogObjectHolder;
import cn.ibdsr.web.config.properties.WebProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * spring session配置
 *
 * @author fengshuonan
 * @date 2017-07-13 21:05
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)  //session过期时间  如果部署多机环境,需要打开注释
@ConditionalOnProperty(prefix = "guns", name = "spring-session-open", havingValue = "true")
public class SpringSessionConfig {

    @Value(value = "${spring.redis.database}")
    private int redisDatabase;

    @Bean
    public CookieSerializer cookieSerializer(WebProperties webProperties) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        if (redisDatabase == 0) {
            serializer.setCookieName("JSESSIONID");
        }
        if (redisDatabase == 1) {
            serializer.setCookieName("JSESSIONID_SHOP");
        }
        serializer.setDomainName(webProperties.getDomainName());
        serializer.setCookiePath("/");
        return serializer;
    }

    @EventListener
    public void onSession(SessionDestroyedEvent event) { // 监听session被销毁的事件
        String sessionId = event.getSessionId();
        LogObjectHolder.me().remove(sessionId);
    }

}
