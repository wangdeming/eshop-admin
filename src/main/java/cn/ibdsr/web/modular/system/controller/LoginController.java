package cn.ibdsr.web.modular.system.controller;

import cn.ibdsr.core.base.controller.BaseController;
import cn.ibdsr.core.node.MenuNode;
import cn.ibdsr.core.shiro.ShiroKit;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.shiro.ShopData;
import cn.ibdsr.core.sso.SSOUtil;
import cn.ibdsr.core.support.HttpKit;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.Const;
import cn.ibdsr.web.common.constant.state.LogType;
import cn.ibdsr.web.common.constant.state.PlatformType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserAccountType;
import cn.ibdsr.web.common.constant.state.shop.ShopUserStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.exception.InvalidKaptchaException;
import cn.ibdsr.web.common.persistence.dao.ShopMapper;
import cn.ibdsr.web.common.persistence.dao.ShopUserMapper;
import cn.ibdsr.web.common.persistence.model.Shop;
import cn.ibdsr.web.common.persistence.model.ShopUser;
import cn.ibdsr.web.config.properties.WebProperties;
import cn.ibdsr.web.core.log.LogManager;
import cn.ibdsr.web.core.log.factory.LogTaskFactory;
import cn.ibdsr.web.core.shiro.token.AccountToken;
import cn.ibdsr.web.core.util.ApiMenuFilter;
import cn.ibdsr.web.core.util.KaptchaUtil;
import cn.ibdsr.web.modular.system.dao.MenuDao;
import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * 登录控制器
 *
 * @author fengshuonan
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private WebProperties webProperties;

    @Autowired
    private ShopUserMapper shopUserMapper;

    @Autowired
    private ShopMapper shopMapper;

    @Value(value = "${spring.redis.database}")
    private int redisDatabase;

    /**
     * 商家端-跳转到主页
     */
    @GetMapping(value = "/")
    public String index(Model model) {
        ShiroUser shiroUser = ShiroKit.getUser();
        // 获取菜单列表
        List<Long> roleList = shiroUser.getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/login.html";
        }
        List<MenuNode> menus = menuDao.getMenusByRoleIds(roleList);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);
        model.addAttribute("titles", titles);
        // 用户头像
        ShopData shopData = (ShopData) shiroUser.getData();
        model.addAttribute("avatar", shopData.getAvatar());
        model.addAttribute("platformType", shiroUser.getPlatformType());
        return "/index.html";
    }

    /**
     * 平台端-跳转到主页
     */
    @GetMapping(value = "/{areaCode}")
    public String index(@PathVariable String areaCode, Model model) {
        //获取菜单列表
        Long role = ShiroKit.getUser().getSysAndRoleMap().get(webProperties.getCurSystem());
        // 不存在角色，logout后重定向至统一登录页面
        if (null == role) {
            ShiroKit.getSubject().logout();
            return REDIRECT + "/" + areaCode;
        }

        areaCode = areaCode.toUpperCase();
        if (!ShiroKit.getUser().getAreaCode().equals(areaCode)) {
            return REDIRECT + webProperties.getMasterLoginUrl() + "/" + areaCode;
        }
        List<MenuNode> menus = SSOUtil.listMenusByRoleId(role);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);
        getSession().setAttribute("indexUrl", webProperties.getMasterDomain() + "/" + areaCode);
        return "/index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/";
        } else {
            return "/login.html";
        }
    }

    /**
     * 店铺端登录
     */
    @RequestMapping(value = "/shopLogin", method = RequestMethod.POST)
    public String shopLogin(Integer platformType) {
        platformType = PlatformType.SHOP.getCode();
        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        // 验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        AccountToken token = new AccountToken(username, password.toCharArray());
        // 平台类型
        token.setPlatformType(platformType);
        // 是否记住密码
        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        Subject currentUser = ShiroKit.getSubject();
        currentUser.login(token);
        ShiroUser shiroUser = ShiroKit.getUser();
        //商家端登录，如果使用默认密码。强制提示修改密码。
        ShopUser shopUser = shopUserMapper.selectById(shiroUser.getId());
        //主账户默认密码为法人身份证后6位
        if (StringUtils.equals(shopUser.getAccountType(), String.valueOf(ShopUserAccountType.PARENT.getCode()))) {
            Shop shop = shopMapper.selectById(shopUser.getShopId());
            if (shop == null) {
                throw new BussinessException(BizExceptionEnum.DATA_ERROR);
            }
            if (StringUtils.isNotBlank(shop.getIdentityId())) {
                if (StringUtils.equals(password, StringUtils.substring(shop.getIdentityId(), shop.getIdentityId().length() - 6, shop.getIdentityId().length()))) {
                    return "/updateDefaultPassword.html";
                }
            }
        }
        //子账号默认密码为111111
        if (StringUtils.equals(shopUser.getAccountType(), String.valueOf(ShopUserAccountType.SUB.getCode()))) {
            if (StringUtils.equals(password, Const.DEFAULT_PASSWORD)) {
                return "/updateDefaultPassword.html";
            }
        }

        //登录成功设置账户为激活状态
        ShopUser user = new ShopUser();
        user.setId(shiroUser.getId());
        user.setStatus(ShopUserStatus.OK.getCode());
        user.setModifiedUser(shopUser.getId());
        user.setModifiedTime(new Date());
        user.updateById();

        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getName());

        // 登录日志
        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), HttpKit.getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);
        return REDIRECT + "/";
    }

    /**
     * 退出登录
     */
    @GetMapping(value = "/logout")
    public String logOut() {
        if (PlatformType.SHOP.getCode() == ShiroKit.getUser().getPlatformType()) {
            // 商家
            LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), HttpKit.getIp()));
            ShiroKit.getSubject().logout();
            return REDIRECT + "/";
        } else {
            // 平台
            SSOUtil.addLoginLog(webProperties.getMasterDomain(), ShiroKit.getUser(), LogType.EXIT.getMessage(),
                    getHttpServletRequest().getRemoteAddr(), webProperties.getCurSystem());
            String areaCode = ShiroKit.getUser().getAreaCode();
            ShiroKit.getSubject().logout();
            return REDIRECT + "/" + areaCode;
        }
    }

}
