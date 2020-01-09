package cn.appsys.service;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
    DevUser login(String devCode,String devPassword);
}

