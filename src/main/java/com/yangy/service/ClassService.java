package com.yangy.service;

import com.yangy.entity.Tclass;

import java.util.List;

public interface ClassService {
    void addClass(Tclass tclass); // 添加班级信息

    void deleteClassById(String id); // 根据ID删除班级信息

    void updateClassById(Tclass tclass); // 根据ID更新班级信息

    Class getClassById(String id); // 根据ID获取班级信息

    List<Class> getAllClasses(); // 获取所有班级信息

    String getClassName(String id);

}
