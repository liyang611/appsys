package cn.appsys.service;

import cn.appsys.pojo.BackendUser;

public interface BakendService {
    BackendUser longin(String username,String passwrd);
}
