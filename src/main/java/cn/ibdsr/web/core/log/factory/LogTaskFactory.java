package cn.ibdsr.web.core.log.factory;

import cn.ibdsr.core.db.Db;
import cn.ibdsr.core.shiro.ShiroUser;
import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.LogSucceed;
import cn.ibdsr.web.common.constant.state.LogType;
import cn.ibdsr.web.common.persistence.dao.LoginLogMapper;
import cn.ibdsr.web.common.persistence.dao.OperationLogMapper;
import cn.ibdsr.web.common.persistence.model.LoginLog;
import cn.ibdsr.web.common.persistence.model.OperationLog;
import cn.ibdsr.web.core.log.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author fengshuonan
 * @date 2016年12月6日 下午9:18:27
 */
public class LogTaskFactory {

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);
    private static OperationLogMapper operationLogMapper = Db.getMapper(OperationLogMapper.class);
    private static LoginLogMapper loginLogMapper = Db.getMapper(LoginLogMapper.class);

    public static TimerTask loginLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LoginLog loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip);
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String username, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(
                        LogType.LOGIN_FAIL, null, "账号:" + username + "," + msg, ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Long userId, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LoginLog loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null, ip);
                try {
                    loginLogMapper.insert(loginLog);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final ShiroUser user, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(LogType.BUSSINESS, user.getId(), user.getName(), user.getAccount(),
                        bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final ShiroUser shiroUser, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtil.getExceptionMsg(exception);
                Long userId = null;
                String userName = null;
                String userAccount = null;
                if (null != shiroUser) {
                    userId = shiroUser.getId();
                    userName = shiroUser.getName();
                    userAccount = shiroUser.getAccount();
                }
                OperationLog operationLog = LogFactory.createOperationLog(LogType.EXCEPTION, userId, userName, userAccount,
                        "", null, null, msg, LogSucceed.FAIL);
                try {
                    operationLogMapper.insert(operationLog);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }
}
