package cn.ibdsr.web.core.shiro.token;

import org.apache.shiro.authc.UsernamePasswordToken;

public class AccountToken extends UsernamePasswordToken {

    /**
     * 登录账号类型（1-系统平台；2-店铺；）
     */
    private Integer platformType;

    public AccountToken(String username, char[] password) {
        super(username, password);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public char[] getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(char[] password) {
        super.setPassword(password);
    }

    @Override
    public Object getPrincipal() {
        return super.getPrincipal();
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }

    @Override
    public boolean isRememberMe() {
        return super.isRememberMe();
    }

    @Override
    public void setRememberMe(boolean rememberMe) {
        super.setRememberMe(rememberMe);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}
