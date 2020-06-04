package cn.ibdsr.web.core.shiro;

import cn.ibdsr.web.common.constant.state.PlatformType;
import cn.ibdsr.web.core.shiro.token.AccountToken;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @Description 根据不同的登录类型，将内容交给不同的realm进行处理。
 * @Author Administrator.xiaorongsheng
 * @Date created in 2018/4/26 17:28
 * @Modifed by
 */
public class CustomizedModularRealm extends ModularRealmAuthenticator {

    private Map<String, Object> definedRealms;

    /**
     * 多个realm实现
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        return super.doMultiRealmAuthentication(realms, token);
    }

    /**
     * 调用单个realm执行操作
     */
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(Realm realm, AuthenticationToken token) {
        // 如果该realms不支持(不能验证)当前token
        if (!realm.supports(token)) {
            throw new ShiroException("token错误!");
        }
        AuthenticationInfo info = realm.getAuthenticationInfo(token);
        if (info == null) {
            throw new ShiroException("token不存在!");
        }
        return info;
    }

    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.assertRealmsConfigured();
        Realm realm = null;
        AccountToken token = (AccountToken) authenticationToken;
        //平台端登录调用realm
        if (token.getPlatformType() == PlatformType.PLATFORM.getCode()) {
            realm = (Realm) this.definedRealms.get("platformRealm");
        }
        //代理商登录调用realm
        if (token.getPlatformType() == PlatformType.SHOP.getCode()) {
            realm = (Realm) this.definedRealms.get("shopRealm");
        }
        if (realm == null) {
            return null;
        }
        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    /**
     * 判断realm是否为空
     */
    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        this.definedRealms = this.getDefinedRealms();
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("值传递错误!");
        }
    }

    public Map<String, Object> getDefinedRealms() {
        return this.definedRealms;
    }

    public void setDefinedRealms(Map<String, Object> definedRealms) {
        this.definedRealms = definedRealms;
    }

}
