package cn.ibdsr.web.core.shiro.realm;

import cn.ibdsr.core.sso.SSOUtil;
import cn.ibdsr.core.util.SpringContextHolder;
import cn.ibdsr.web.config.properties.WebProperties;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class PlatformRealm extends AuthorizingRealm {

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        return new SimpleAuthenticationInfo();
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        WebProperties webProperties = SpringContextHolder.getBean(WebProperties.class);
        return SSOUtil.getAuthorizationByRoleId(webProperties.getMasterDomain(), cn.ibdsr.core.shiro.ShiroKit.getUser().getSysAndRoleMap().get(webProperties.getCurSystem()));
    }

    /**
     * 设置认证加密方式
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(cn.ibdsr.core.shiro.ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(cn.ibdsr.core.shiro.ShiroKit.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }

}
