package cn.appsys.service.impl;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.service.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Resource
    private DevUserMapper devUserMapper;
}
