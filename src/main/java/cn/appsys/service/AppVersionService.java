package cn.appsys.service;

import cn.appsys.pojo.AppVersion;

import java.util.List;

public interface AppVersionService {
    List<AppVersion> selectByAppId(Long appId);
}
