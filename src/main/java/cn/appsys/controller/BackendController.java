package cn.appsys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend")
public class BackendController {
    @RequestMapping("/login")
    public String login() {
        return "backendlogin";
    }
}
