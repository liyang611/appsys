package cn.appsys.service.impl;

import cn.appsys.dao.AppInfoMapper;
import cn.appsys.dao.AppVersionMapper;
import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppVersionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public List<AppVersion> selectByAppId(Long appId) {
        List<AppVersion> appVersionList = appVersionMapper.selectList(new EntityWrapper<AppVersion>().eq("appId", appId));
        for (AppVersion appVersion : appVersionList) {
            appVersion.setAppname(appInfoMapper.selectById(appId).getSoftwarename());
            appVersion.setPublishstatusname(dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode", "PUBLISH_STATUS").eq("valueId", appVersion.getPublishstatus())).get(0).getValuename());
        }
        return appVersionList;
    }
}
