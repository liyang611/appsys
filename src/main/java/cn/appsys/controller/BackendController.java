package cn.appsys.controller;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.BakendService;
import cn.appsys.service.DataDictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/backend")
/*后台管理*/
public class BackendController {
    @RequestMapping("/login")
    public String login() {
        return "backendlogin";
    }

    @Resource
    private BakendService bakendService;
    @Resource
    private DataDictionaryService dataDictionaryService;

    @RequestMapping(value = "dologin", method = RequestMethod.POST)
    public String daolongin(HttpSession session, String userCode, String userPassword) {
        BackendUser user = bakendService.longin(userCode, userPassword);
        if (user == null) {
            session.setAttribute("error", "用户名或密码错误");
            return "redirect:/backend/login";
        } else {
            DataDictionary dataDictionary = dataDictionaryService.selectByValueId(user.getUsertype());
            user.setDataDictionary(dataDictionary);
            session.removeAttribute("error");
            session.setAttribute("userSession", user);
            return "backend/main";
        }
    }
}
