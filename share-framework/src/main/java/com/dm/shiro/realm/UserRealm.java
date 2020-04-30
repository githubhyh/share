package com.dm.shiro.realm;

import com.dm.beans.AuthToken;
import com.dm.common.GlobalConstant;
import com.dm.domain.system.user.SysUser;
import com.dm.enums.AuthType;
import com.dm.exception.runtime.UserLockedException;
import com.dm.exception.runtime.UserNotFoundException;
import com.dm.exception.runtime.UserPasswordNotMatchException;
import com.dm.exception.runtime.UserValidateException;
import com.dm.service.system.user.ISysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author hu.yuhao
 * @date 2020-04-26
 * */
public class UserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysUserService sysUserService;
    /**
     * 授权
     * 验证时会获取授权信息
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        info.addStringPermission("*:*:*");  //所有权限
        return info;
    }


    /**
     * 验证（验明真身）
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        char[] password = token.getPassword();
        SysUser sysUser = new SysUser();
        sysUser.setLoginID(username);
        sysUser.setPassword(new String(password));
        AuthToken loginToken = sysUserService.login(sysUser);
        try {
            if (loginToken.getType() == AuthType.UNAUTH && GlobalConstant.LOGIN_ACCOUNT_ERROR.equals(loginToken.getMessage())) {
                throw new UserPasswordNotMatchException(GlobalConstant.LOGIN_ACCOUNT_ERROR);
            }
            if (loginToken.getType() == AuthType.UNAUTH && GlobalConstant.LOGIN_ACCOUNT_LOCKED.equals(loginToken.getMessage())) {
                throw new UserLockedException(GlobalConstant.LOGIN_ACCOUNT_LOCKED);
            }
            if (loginToken.getType() == AuthType.UNAUTH && GlobalConstant.LOGIN_ACCOUNT_NOT_REGISTER.equals(loginToken.getMessage())) {
                throw new UserNotFoundException(GlobalConstant.LOGIN_ACCOUNT_NOT_REGISTER);
            }
            if (loginToken.getType() == AuthType.UNAUTH) {
                throw new UserValidateException(GlobalConstant.VALIDATE_ILLEGAL);
            }
        }catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        }catch (UserLockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        }catch (UserNotFoundException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        }catch (UserValidateException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        }catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new SimpleAuthenticationInfo(username, sysUser.getPassword(), getName());
    }
}
