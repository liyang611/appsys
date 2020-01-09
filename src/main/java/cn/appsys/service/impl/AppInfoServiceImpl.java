package cn.appsys.service.impl;

import cn.appsys.dao.AppInfoMapper;
import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.dao.AppVersionMapper;
import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppInfoService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AppInfoServiceImpl implements AppInfoService {
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private AppCategoryMapper appCategoryMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;
    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public Page<AppInfo> selectPage(Integer pageIndex, Integer pageSize, String querySoftwareName, Integer queryStatus, Integer queryFlatformId, Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3) {
        Page<AppInfo> page = new Page<>(pageIndex, pageSize);
        //条件构造器
        EntityWrapper<AppInfo> wrapper = new EntityWrapper<>();
        if (querySoftwareName != null && !"".equals(querySoftwareName)) {
            wrapper.like("softwareName", querySoftwareName);
        }
        if (queryStatus != null && queryStatus != 0) {
            wrapper.eq("status", queryStatus);
        }
        if (queryFlatformId != null && queryFlatformId != 0) {
            wrapper.eq("flatformId", queryFlatformId);
        }
        if (queryCategoryLevel1 != null && queryCategoryLevel1 != 0) {
            wrapper.eq("categoryLevel1", queryCategoryLevel1);
        }
        if (queryCategoryLevel2 != null && queryCategoryLevel2 != 0) {
            wrapper.eq("categoryLevel2", queryCategoryLevel2);
        }
        if (queryCategoryLevel3 != null && queryCategoryLevel3 != 0) {
            wrapper.eq("categoryLevel3", queryCategoryLevel3);
        }
        List<AppInfo> appInfos = appInfoMapper.selectPage(page, wrapper);
        for (AppInfo appInfo : appInfos) {
            appInfo.setCategorylevel1name(appCategoryMapper.selectById(appInfo.getCategorylevel1()).getCategoryname());
            appInfo.setCategorylevel2name(appCategoryMapper.selectById(appInfo.getCategorylevel2()).getCategoryname());
            appInfo.setCategorylevel3name(appCategoryMapper.selectById(appInfo.getCategorylevel3()).getCategoryname());
            appInfo.setStatusname(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode", "APP_STATUS").eq("valueid", appInfo.getStatus())).get(0).getValuename());
            appInfo.setFlatformname(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode", "APP_FLATFORM").eq("valueid", appInfo.getFlatformid())).get(0).getValuename());
            Long versionid = appInfo.getVersionid();
            if (versionid != null) {
                AppVersion appVersion = appVersionMapper.selectById(versionid);
                appInfo.setVersionno(appVersion.getVersionno());
            }
        }
        page.setRecords(appInfos);
        return page;
    }

    @Override
    public AppInfo selectById(Long id) {
        AppInfo appInfo = appInfoMapper.selectById(id);
        appInfo.setCategorylevel1name(appCategoryMapper.selectById(appInfo.getCategorylevel1()).getCategoryname());
        appInfo.setCategorylevel2name(appCategoryMapper.selectById(appInfo.getCategorylevel2()).getCategoryname());
        appInfo.setCategorylevel3name(appCategoryMapper.selectById(appInfo.getCategorylevel3()).getCategoryname());
        appInfo.setStatusname(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode", "APP_STATUS").eq("valueid", appInfo.getStatus())).get(0).getValuename());
        appInfo.setFlatformname(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode", "APP_FLATFORM").eq("valueid", appInfo.getFlatformid())).get(0).getValuename());
        Long versionid = appInfo.getVersionid();
        if (versionid != null) {
            AppVersion appVersion = appVersionMapper.selectById(versionid);
            appInfo.setVersionno(appVersion.getVersionno());
        }
        return appInfo;
    }

    @Override
    public Boolean deleteFile(Long id) {
        AppInfo appInfo = appInfoMapper.selectById(id);
        if (appInfo == null) {
            return false;
        } else {
            appInfo.setLogopicpath(null);
            appInfo.setLogolocpath(null);
            Integer i = appInfoMapper.updateById(appInfo);
            if (i == 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public Boolean add(AppInfo appInfo) {
        Integer i = appInfoMapper.insert(appInfo);
        if (i == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<AppInfo> selectByAPKName(String APKName) {
        return appInfoMapper.selectList(new EntityWrapper<AppInfo>().eq("APKName", APKName));
    }

    @Override
    public Boolean modify(AppInfo appInfo) {
        Integer i = appInfoMapper.updateById(appInfo);
        if (i == 0) {
            return false;
        } else {
            return true;
        }
    }
}
