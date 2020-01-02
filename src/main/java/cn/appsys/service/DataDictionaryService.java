package cn.appsys.service;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
    DataDictionary selectByValueId(Long valueId);
}
