package cn.ibdsr.web.core.listener;

import cn.ibdsr.core.sso.SSOUtil;
import cn.ibdsr.web.config.properties.WebProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description Spring容器启动完成事件监听器
 * @Version V1.0
 * @CreateDate 2019/4/12 13:55
 *
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/4/12      wangzhipeng            类说明
 */
@Component
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private WebProperties webProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        SSOUtil.setMasterDomain(webProperties.getMasterDomain());
    }
}
