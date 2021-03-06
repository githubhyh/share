package com.dm.action.system.user;

import com.dm.beans.AuthToken;
import com.dm.domain.system.user.SysUser;
import com.dm.exception.runtime.NoSuchTypeException;
import com.dm.service.system.user.ISysUserService;
import com.dm.test.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class SysUserAction {
    private static final Logger logger = LoggerFactory.getLogger(SysUserAction.class);

    private String prefix = "system/user/passwd";

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private TestService service;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/add")
    @ResponseBody
    public AuthToken add(SysUser user, HttpServletRequest request) {
        return service.register(user);
    }

    @GetMapping("/findPasswd")
    public String findPasswd() {
        return prefix + "/findpasswd";
    }
}
