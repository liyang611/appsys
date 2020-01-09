package cn.appsys.service.impl;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;

    @Override
    public DevUser login(String usercode, String userpassword) {
        DevUser u = new DevUser();
        u.setDevcode(usercode);
        DevUser user = devUserMapper.selectOne(u);
        if (user != null && user.getDevpassword().equals(userpassword)) {
            return user;
        }
        return null;
    }
}
