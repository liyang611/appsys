package cn.appsys.service;

import cn.appsys.pojo.BackendUser;

public interface BakendUserService {
    BackendUser login(String usercode,String passwrd);
}
