package cn.appsys.service.impl;

import cn.appsys.dao.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

    @Override
    public DataDictionary selectByValueId(Long valueId) {
        return dataDictionaryMapper.selectList(new EntityWrapper<DataDictionary>().eq("valueId", valueId)).get(0);
    }
}
