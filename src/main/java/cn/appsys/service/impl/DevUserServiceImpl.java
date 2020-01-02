package cn.appsys.service.impl;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;

    @Override
    public DevUser longin(String username, String passwrd) {
    DevUser u=new DevUser();
    u.setDevcode(username);
    DevUser user=devUserMapper.selectOne(u);
    if (user!=null&&user.getDevpassword().equals(passwrd)){
        return user;
    }
  return null;
    }
}
