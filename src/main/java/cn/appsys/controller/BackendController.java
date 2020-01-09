package cn.appsys.controller;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.BakendUserService;
import cn.appsys.service.DataDictionaryService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/backend")
/*后台管理*/
public class BackendController {
    @RequestMapping("/login")
    public String login() {
        return "backendlogin";
    }

    @Resource
    private BakendUserService bakendUserService;
    @Resource
    private DataDictionaryService dataDictionaryService;
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppCategoryService appCategoryService;

    @RequestMapping(value = "dologin", method = RequestMethod.POST)
    public String daolongin(HttpSession session, String userCode, String userPassword) {
        BackendUser user = bakendUserService.login(userCode, userPassword);
        if (user == null) {
            session.setAttribute("error", "用户名或密码错误");
            return "redirect:/backend/login";
        } else {
            DataDictionary dataDictionary = dataDictionaryService.selectUserType(user.getUsertype());
            user.setDataDictionary(dataDictionary);
            session.removeAttribute("error");
            session.setAttribute("userSession", user);
            return "redirect:/backend/main";
        }
    }

    @RequestMapping("/main")
    public String main() {
        return "backend/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/backend/login";
    }

    @RequestMapping("/app/list")
    public String list(HttpSession session, Integer pageIndex,
                       @RequestParam(required = false) String querySoftwareName,
                       @RequestParam(required = false) Integer queryFlatformId,
                       @RequestParam(required = false) Integer queryCategoryLevel1,
                       @RequestParam(required = false) Integer queryCategoryLevel2,
                       @RequestParam(required = false) Integer queryCategoryLevel3) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<AppInfo> page = appInfoService.selectPage(pageIndex, 5, querySoftwareName, 1, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
        List<DataDictionary> flatFormList = dataDictionaryService.selectAllFlatForm();
        List<AppCategory> categoryLevel1List = appCategoryService.selectAppCategoryLevel1();

        session.setAttribute("querySoftwareName", querySoftwareName);
        session.setAttribute("queryFlatformId", queryFlatformId);
        session.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        session.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        session.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        session.setAttribute("flatFormList", flatFormList);
        session.setAttribute("categoryLevel1List", categoryLevel1List);
        session.setAttribute("appInfoList", page.getRecords());
        session.setAttribute("pages", page);
        return "backend/applist";
    }

    @RequestMapping("/categorylevellist")
    @ResponseBody
    public Object categorylevellist(Long pid) {
        return appCategoryService.selectAppCategories(pid);
    }
}
