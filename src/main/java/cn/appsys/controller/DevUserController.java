package cn.appsys.controller;

import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/dev")
public class DevUserController {
    @Resource
    private DevUserService devUserService;
    @RequestMapping("/login")
    public String login(){
        return "devlogin";
    }
    @RequestMapping("/longinto")
    public String longinto(){return "backendlog";}
}
