package cn.appsys.service.impl;

import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public DataDictionary selectUserType(Long userTypeId) {
        return dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode","USER_TYPE").eq("valueId", userTypeId)).get(0);
    }

    @Override
    public List<DataDictionary> selectAllFlatForm() {
        return dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode","APP_FLATFORM"));
    }

    @Override
    public List<DataDictionary> selectAllStatus() {
        return dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("typeCode","APP_STATUS"));
    }
}
