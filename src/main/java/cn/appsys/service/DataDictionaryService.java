package cn.appsys.service;

import cn.appsys.pojo.DataDictionary;

import java.util.List;

public interface DataDictionaryService {
    DataDictionary selectUserType(Long userTypeId);
    List<DataDictionary> selectAllFlatForm();
    List<DataDictionary> selectAllStatus();
}
