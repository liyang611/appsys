package cn.appsys.service.impl;

import cn.appsys.dao.BackendMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BakendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BackendServicelmpl implements BakendService {
    @Resource
    private BackendMapper backendMapper;

    @Override
    public BackendUser longin(String username, String passwrd) {
        BackendUser u = new BackendUser();
        u.setUsercode(username);
        BackendUser user = backendMapper.selectOne(u);
        if (user != null && user.getUserpassword().equals(passwrd)) {
            return user;
        }
        return null;
    }
}
