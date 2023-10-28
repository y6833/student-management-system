package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher getTeacherByRoleId(String roleId);

    void saveTeacherList(List<Teacher> list);

    List<Teacher> findAllOver();

    IPage<Teacher> getPage(IPage<Teacher> page, QueryWrapper<Teacher> queryWrapper);

    boolean saveTeacher(Teacher teacher);

    boolean updataTeacher(Teacher teacher);

    List<Teacher> findAll();

    boolean removeByTeaId(String id);
}
