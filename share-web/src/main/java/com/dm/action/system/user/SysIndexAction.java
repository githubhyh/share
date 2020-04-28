package com.dm.action.system.user;

import com.dm.action.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SysIndexAction extends BaseAction {
    @GetMapping("/")
    public String root() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
