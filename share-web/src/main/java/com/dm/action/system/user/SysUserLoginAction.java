package com.dm.action.system.user;

import com.dm.action.BaseAction;
import com.dm.beans.AuthToken;
import com.dm.domain.system.user.SysUser;
import com.dm.service.system.user.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SysUserLoginAction extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(SysUserLoginAction.class);

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AuthToken login(SysUser user, HttpServletRequest request) {
        AuthToken login = sysUserService.login(user);
        return login;
    }
}
