package cn.appsys.service.impl;

import cn.appsys.dao.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BakendUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BackendUserServicelmpl implements BakendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;

    @Override
    public BackendUser login(String usercode, String userpassword) {
        BackendUser u = new BackendUser();
        u.setUsercode(usercode);
        BackendUser user = backendUserMapper.selectOne(u);
        if (user != null && user.getUserpassword().equals(userpassword)) {
            return user;
        }
        return null;
    }
}
