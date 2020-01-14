package cn.appsys.controller;

import cn.appsys.pojo.*;
import cn.appsys.service.*;
import cn.appsys.tools.ApkNameData;
import cn.appsys.tools.ResultData;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/dev")
/*开发者*/
public class DevUserController {
    @Resource
    private DevUserService devUserService;
    @Resource
    private AppInfoService appInfoService;
    @Resource
    private AppCategoryService appCategoryService;
    @Resource
    private AppVersionService appVersionService;
    @Resource
    private DataDictionaryService dataDictionaryService;

    @RequestMapping("/login")
    public String login() {
        return "devlogin";
    }

    @RequestMapping("/dologin")
    public String doLogin(HttpSession session, String devCode, String devPassword) {
        DevUser devUser = devUserService.login(devCode, devPassword);
        if (devUser == null) {
            session.setAttribute("error", "用户名或密码错误");
            return "redirect:/dev/login";
        } else {
            session.removeAttribute("error");
            session.setAttribute("devUserSession", devUser);
            return "redirect:/dev/main";
        }
    }

    @RequestMapping("/main")
    public String main() {
        return "developer/main";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/dev/login";
    }

    @RequestMapping("/app/list")
    public String appList(HttpSession session, Integer pageIndex,
                          @RequestParam(required = false) String querySoftwareName,
                          @RequestParam(required = false) Integer queryStatus,
                          @RequestParam(required = false) Integer queryFlatformId,
                          @RequestParam(required = false) Integer queryCategoryLevel1,
                          @RequestParam(required = false) Integer queryCategoryLevel2,
                          @RequestParam(required = false) Integer queryCategoryLevel3) {
        if (pageIndex == null) {
            pageIndex = 1;
        }
        Page<AppInfo> page = appInfoService.selectPage(pageIndex, 5, querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
        List<DataDictionary> statusList = dataDictionaryService.selectAllStatus();
        List<DataDictionary> flatFormList = dataDictionaryService.selectAllFlatForm();
        List<AppCategory> categoryLevel1List = appCategoryService.selectAppCategoryLevel1();

        session.setAttribute("querySoftwareName", querySoftwareName);
        session.setAttribute("queryStatus", queryStatus);
        session.setAttribute("statusList", statusList);
        session.setAttribute("queryFlatformId", queryFlatformId);
        session.setAttribute("queryCategoryLevel1", queryCategoryLevel1);
        session.setAttribute("queryCategoryLevel2", queryCategoryLevel2);
        session.setAttribute("queryCategoryLevel3", queryCategoryLevel3);
        session.setAttribute("flatFormList", flatFormList);
        session.setAttribute("categoryLevel1List", categoryLevel1List);
        session.setAttribute("appInfoList", page.getRecords());
        session.setAttribute("pages", page);
        return "developer/appinfolist";
    }

    @RequestMapping("/categorylevellist")
    @ResponseBody
    public Object categoryLevelList(Long pid) {
        if (pid == null) {
            return appCategoryService.selectAppCategoryLevel1();
        } else {
            return appCategoryService.selectAppCategories(pid);
        }
    }

    @RequestMapping("/app/appview/{id}")
    public String appView(HttpSession session, @PathVariable String id) {
        Long appId = Long.parseLong(id);
        AppInfo appInfo = appInfoService.selectById(appId);
        List<AppVersion> appVersionList = appVersionService.selectByAppId(appId);
        session.setAttribute("appInfo", appInfo);
        session.setAttribute("appVersionList", appVersionList);
        return "developer/appinfoview";
    }

    @RequestMapping("/app/appinfoadd")
    public String appInfoAdd() {
        return "developer/appinfoadd";
    }

    @RequestMapping("/apkexist")
    @ResponseBody
    public Object apkExist(String APKName) {
        ApkNameData apkNameData = new ApkNameData();
        if (APKName == null || "".equals(APKName)) {
            apkNameData.setApkName("empty");
        } else {
            List<AppInfo> list = appInfoService.selectByAPKName(APKName);
            if (list.size() != 0) {
                apkNameData.setApkName("exist");
            } else {
                apkNameData.setApkName("noexist");
            }
        }
        return apkNameData;
    }

    @RequestMapping("/app/appinfoaddsave")
    public String appInfoAddSave(AppInfo appInfo, HttpServletRequest request, @RequestParam(required = false) MultipartFile a_logoPicPath) {
        String logopicpath = null;
        String logolocpath = null;
        if (!a_logoPicPath.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = a_logoPicPath.getOriginalFilename();
            String prefix = FilenameUtils.getExtension(oldFileName);
            int fileSize = 500000;
            if (a_logoPicPath.getSize() > fileSize) {
                request.setAttribute("fileUploadError", "上传文件过大！");
                return "developer/appinfoadd";
            } else if ("jpg".equalsIgnoreCase(prefix) || "png".equalsIgnoreCase(prefix) || "jpeg".equalsIgnoreCase(prefix) || "pneg".equalsIgnoreCase(prefix)) {
                String filename = appInfo.getApkname() + ".jpg";
                File targetFile = new File(path, filename);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    a_logoPicPath.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    request.setAttribute("fileUploadError", "上传失败！");
                    return "developer/appinfoadd";
                }
                logopicpath = request.getContextPath() + "/statics/uploadfiles/" + filename;
                logolocpath = path + File.separator + filename;
            } else {
                request.setAttribute("fileUploadError", "上传文件格式不正确！");
                return "developer/appinfoadd";
            }
        }
        appInfo.setLogopicpath(logopicpath);
        appInfo.setLogolocpath(logolocpath);
        appInfo.setCreatedby(((DevUser) request.getSession().getAttribute("devUserSession")).getId());
        appInfo.setCreationdate(new Date());
        appInfo.setDevid(((DevUser) request.getSession().getAttribute("devUserSession")).getId());
        appInfo.setStatus(1L);
        if (appInfoService.add(appInfo)) {
            return "redirect:/dev/app/list";
        }
        return "developer/appinfoadd";
    }

    @RequestMapping("/app/appinfomodify")
    public String appInfoModify(HttpSession session, Long id, @RequestParam(required = false) String error) {
        if (error != null && "error1".equals(error)) {
            error = "上传文件过大！";
        } else if (error != null && "error2".equals(error)) {
            error = "上传失败！";
        } else if (error != null && "error3".equals(error)) {
            error = "上传文件格式不正确！";
        }
        AppInfo appInfo = appInfoService.selectById(id);
        session.setAttribute("appInfo", appInfo);
        session.setAttribute("fileUploadError", error);
        return "developer/appinfomodify";
    }

    @RequestMapping("/datadictionarylist")
    @ResponseBody
    public Object dataDictionaryList() {
        return dataDictionaryService.selectAllFlatForm();
    }

    @RequestMapping("/delfile")
    @ResponseBody
    public Object delFile(@RequestParam(required = false) Long id,
                          @RequestParam(required = false) String flag) {
        ResultData resultData = new ResultData();
        String fileLocPath;
        if (flag == null || "".equals(flag) || id == null || id == 0) {
            resultData.setResult("failed");
        } else if ("logo".equals(flag)) {
            fileLocPath = appInfoService.selectById(id).getLogolocpath();
            File file = new File(fileLocPath);
            if (file.exists()) {
                if (file.delete()) {
                    if (appInfoService.deleteFile(id)) {
                        resultData.setResult("success");
                    }
                }
            } else {
                resultData.setResult("failed");
            }
        } else if ("apk".equals(flag)) {
            fileLocPath = appVersionService.selectById(id).getApklocpath();
            File file = new File(fileLocPath);
            if (file.exists()) {
                if (file.delete()) {
                    if (appVersionService.deleteFile(id)) {
                        resultData.setResult("success");
                    }
                }
            } else {
                resultData.setResult("failed");
            }
        }
        return resultData;
    }

    @RequestMapping("/app/appinfomodifysave")
    public String appInfoModifySave(AppInfo appInfo, HttpServletRequest request, @RequestParam(required = false) MultipartFile attach) {
        String logopicpath = null;
        String logolocpath = null;
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = attach.getOriginalFilename();
            String prefix = FilenameUtils.getExtension(oldFileName);
            int fileSize = 500000;
            if (attach.getSize() > fileSize) {
                return "redirect:/dev/app/appinfomodify?id=" + appInfo.getId() + "&error=error1";
            } else if ("jpg".equalsIgnoreCase(prefix) || "png".equalsIgnoreCase(prefix) || "jpeg".equalsIgnoreCase(prefix) || "pneg".equalsIgnoreCase(prefix)) {
                String filename = appInfo.getApkname() + ".jpg";
                File targetFile = new File(path, filename);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/app/appinfomodify?id=" + appInfo.getId() + "&error=error2";
                }
                logopicpath = request.getContextPath() + "/statics/uploadfiles/" + filename;
                logolocpath = path + File.separator + filename;
            } else {
                return "redirect:/dev/app/appinfomodify?id=" + appInfo.getId() + "&error=error3";
            }
        }
        appInfo.setLogopicpath(logopicpath);
        appInfo.setLogolocpath(logolocpath);
        appInfo.setModifyby(((DevUser) request.getSession().getAttribute("devUserSession")).getId());
        appInfo.setModifydate(new Date());
        if (appInfoService.modify(appInfo)) {
            return "redirect:/dev/app/list";
        }
        return "developer/appinfomodify";
    }

    @RequestMapping("/delapp")
    @ResponseBody
    public Object delApp(Long id) {
        ResultData resultData = new ResultData();
        AppInfo appInfo = appInfoService.selectById(id);
        if (appInfo == null) {
            resultData.setDelResult("notexist");
        } else {
            if (appInfoService.deleteById(id)) {
                resultData.setDelResult("true");
            } else {
                resultData.setDelResult("false");
            }
        }
        return resultData;
    }

    @RequestMapping("/app/appversionadd")
    public String appVersionAdd(Model model, Long id, AppVersion appVersion, @RequestParam(required = false) String error) {
        if (error != null && "error1".equals(error)) {
            error = "APK信息不完整";
        } else if (error != null && "error2".equals(error)) {
            error = "上传失败";
        } else if (error != null && "error3".equals(error)) {
            error = "上传文件格式不正确";
        }
        appVersion.setAppid(id);
        List<AppVersion> appVersionList = appVersionService.selectByAppId(id);
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute(appVersion);
        model.addAttribute("fileUploadError", error);
        return "developer/appversionadd";
    }

    @RequestMapping("/app/addversionsave")
    public String addVersionSave(AppVersion appVersion, HttpServletRequest request, @RequestParam(required = false) MultipartFile a_downloadLink) {
        String downloadLink = null;
        String apkLocPath = null;
        String apkFileName = null;
        if (!a_downloadLink.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = a_downloadLink.getOriginalFilename();
            String prefix = FilenameUtils.getExtension(oldFileName);
            if ("apk".equalsIgnoreCase(prefix)) {
                String apkname = appInfoService.selectById(appVersion.getAppid()).getApkname();
                if (apkname == null || "".equals(apkname)) {
                    return "redirect:/dev/app/appversionadd?id=" + appVersion.getAppid() + "&error=error1";
                }
                apkFileName = apkname + "-" + appVersion.getVersionno() + ".apk";
                File targetFile = new File(path, apkFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    a_downloadLink.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/app/appversionadd?id=" + appVersion.getAppid() + "&error=error2";
                }
                downloadLink = request.getContextPath() + "/statics/uploadfiles/" + apkFileName;
                apkLocPath = path + File.separator + apkFileName;
            } else {
                return "redirect:/dev/app/appversionadd?id=" + appVersion.getAppid() + "&error=error3";
            }
        }
        appVersion.setCreatedby(((DevUser) request.getSession().getAttribute("devUserSession")).getId());
        appVersion.setCreationdate(new Date());
        appVersion.setDownloadlink(downloadLink);
        appVersion.setApklocpath(apkLocPath);
        appVersion.setApkfilename(apkFileName);
        if (appVersionService.add(appVersion)) {
            return "redirect:/dev/app/list";
        } else {
            return "redirect:/dev/app/appversionadd?id=" + appVersion.getAppid();
        }
    }

    @RequestMapping("/app/appversionmodify")
    public String appVersionModify(Long vid, Long aid, @RequestParam(required = false) String error, Model model) {
        if (error != null && "error1".equals(error)) {
            error = "APK信息不完整";
        } else if (error != null && "error2".equals(error)) {
            error = "上传失败";
        } else if (error != null && "error3".equals(error)) {
            error = "上传文件格式不正确";
        }
        AppVersion appVersion = appVersionService.selectById(vid);
        List<AppVersion> appVersionList = appVersionService.selectByAppId(aid);
        model.addAttribute("appVersionList", appVersionList);
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("fileUploadError", error);
        return "developer/appversionmodify";
    }

    @RequestMapping("/app/appversionmodifysave")
    public String appVersionModifySave(AppVersion appVersion, HttpServletRequest request, @RequestParam(required = false) MultipartFile attach) {
        String downloadLink = null;
        String apkLocPath = null;
        String apkFileName = null;
        if (!attach.isEmpty()) {
            String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
            String oldFileName = attach.getOriginalFilename();
            String prefix = FilenameUtils.getExtension(oldFileName);
            if ("apk".equalsIgnoreCase(prefix)) {
                String apkname = appInfoService.selectById(appVersion.getAppid()).getApkname();
                if (apkname == null || "".equals(apkname)) {
                    return "redirect:/dev/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppid() + "&error=error1";
                }
                apkFileName = apkname + "-" + appVersion.getVersionno() + ".apk";
                File targetFile = new File(path, apkFileName);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                try {
                    attach.transferTo(targetFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/dev/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppid() + "&error=error2";
                }
                downloadLink = request.getContextPath() + "/statics/uploadfiles/" + apkFileName;
                apkLocPath = path + File.separator + apkFileName;
            } else {
                return "redirect:/dev/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppid() + "&error=error3";
            }
        }
        appVersion.setCreatedby(((DevUser) request.getSession().getAttribute("devUserSession")).getId());
        appVersion.setCreationdate(new Date());
        appVersion.setDownloadlink(downloadLink);
        appVersion.setApklocpath(apkLocPath);
        appVersion.setApkfilename(apkFileName);
        if (appVersionService.modify(appVersion)) {
            return "redirect:/dev/app/list";
        } else {
            return "redirect:/dev/app/appversionmodify?vid=" + appVersion.getId() + "&aid=" + appVersion.getAppid();
        }
    }

    @RequestMapping(value = "/{appId}/sale", method = RequestMethod.PUT)
    @ResponseBody
    public Object sale(@PathVariable String appId, HttpSession session) {
        ResultData resultData = new ResultData();
        long id;
        try {
            id = Long.parseLong(appId);
        } catch (Exception e) {
            id = 0L;
        }
        resultData.setErrorCode("0");
        if (id > 0) {
            try {
                DevUser devUser = (DevUser) session.getAttribute("devUserSession");
                if (appInfoService.updateSaleStatus(id, devUser)) {
                    resultData.setResultMsg("success");
                } else {
                    resultData.setResultMsg("failed");
                }
            } catch (Exception e) {
                resultData.setErrorCode("exception000001");
            }
        } else {
            resultData.setErrorCode("param000001");
        }
        return resultData;
    }
}
