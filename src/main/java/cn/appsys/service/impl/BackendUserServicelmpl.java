package cn.appsys.service.impl;

import cn.appsys.dao.BackendUserMapper;
import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.BakendUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class BackendUserServicelmpl implements BakendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public BackendUser login(String usercode, String userpassword) {
        BackendUser u = new BackendUser();
        u.setUsercode(usercode);
        BackendUser user = backendUserMapper.selectOne(u);
        if (user != null && user.getUserpassword().equals(userpassword)) {
            user.setUsertypename(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode","USER_TYPE").eq("valueId", user.getUsertype())).get(0).getValuename());
            return user;
        }
        return null;
    }
}
