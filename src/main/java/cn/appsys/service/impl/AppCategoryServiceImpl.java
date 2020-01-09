package cn.appsys.service.impl;

import cn.appsys.dao.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;

    @Override
    public List<AppCategory> selectAppCategoryLevel1() {
        return appCategoryMapper.selectList(new EntityWrapper<AppCategory>().isNull("parentId"));
    }

    @Override
    public List<AppCategory> selectAppCategories(Long parentId) {
        return appCategoryMapper.selectList(new EntityWrapper<AppCategory>().eq("parentId", parentId));
    }
}
