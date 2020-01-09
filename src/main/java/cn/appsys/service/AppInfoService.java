package cn.appsys.service;

import cn.appsys.pojo.AppInfo;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

public interface AppInfoService {
    Page<AppInfo> selectPage(Integer pageIndex, Integer pageSize,
                             String querySoftwareName, Integer queryStatus, Integer queryFlatformId,
                             Integer queryCategoryLevel1, Integer queryCategoryLevel2, Integer queryCategoryLevel3);
    AppInfo selectById(Long id);
    Boolean deleteFile(Long id);
    Boolean add(AppInfo appInfo);
    List<AppInfo> selectByAPKName(String APKName);
    Boolean modify(AppInfo appInfo);
}
