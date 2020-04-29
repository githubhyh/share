package com.dm.test;

import com.dm.beans.AuthToken;
import com.dm.domain.system.user.SysUser;
import com.dm.service.system.user.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestService {
    @Autowired
    private ISysUserService userService;

    @Transactional
    public AuthToken register(SysUser user) {
        return userService.register(user);
    }
}
