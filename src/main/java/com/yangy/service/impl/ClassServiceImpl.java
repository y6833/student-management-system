package com.yangy.service.impl;

import com.yangy.mapper.ClassMapper;
import com.yangy.entity.Tclass;
import com.yangy.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper; // mapper对象注入

    @Override
    public void addClass(Tclass tclass) { // 添加班级信息
        classMapper.insert(tclass);
    }

    @Override
    public void deleteClassById(String id) { // 根据ID删除班级信息
        classMapper.deleteById(id);
    }

    @Override
    public void updateClassById(Tclass tclass) { // 根据ID更新班级信息
        classMapper.updateById(tclass);
    }

    @Override
    public Class getClassById(String id) { // 根据ID获取班级信息
        return classMapper.selectById(id);
    }

    @Override
    public List<Class> getAllClasses() { // 获取所有班级信息
        return classMapper.selectAll();
    }
}
