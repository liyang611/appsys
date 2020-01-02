package cn.appsys.controller;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dev")
/*开发者*/
public class DevUserController {
    @Resource
    private DevUserService devUserService;
    @RequestMapping("/login")
    public String login(){
        return "devlogin";
    }










}
