package com.dm.filter;

import com.dm.common.GlobalConstant;
import com.dm.utils.StringUtils;
import com.dm.utils.RedisSessionUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    private String loginUrl;

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * 退出登录之前删除用户缓存
     * */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        try {
            Subject subject = getSubject(request, response);
            String redirectUrl = getRedirectUrl(request, response, subject);
            if (StringUtils.isNotNull(subject)) {
                subject.logout();
                logger.info("do logout");
                Session session = subject.getSession();
                if (StringUtils.isNotNull(session)) {
                    RedisSessionUtils.del(GlobalConstant.PREFIX_OF_REDIS_SESSION_KEY + session.getId().toString());
                    logger.info("delete session, session id is [{}]", session.getId().toString());
                }
                issueRedirect(request, response, redirectUrl);
            }
        }catch (Exception e) {
            logger.error("logout fail, error message :{}", e.getMessage());
        }
        return false;
    }

    /**
     * 退出后跳转到login
     * */
    @Override
    public String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
        String loginUrl = getLoginUrl();
        if (StringUtils.isNotNull(loginUrl)) {
            logger.info("get login url :{}", loginUrl);
            return loginUrl;
        }
        return super.getRedirectUrl(request, response, subject);
    }
}
