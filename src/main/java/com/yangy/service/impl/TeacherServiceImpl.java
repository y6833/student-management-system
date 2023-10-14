package com.yangy.service.impl;

import com.yangy.mapper.TeacherMapper;
import com.yangy.entity.Teacher;
import com.yangy.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> findAll() {
        return teacherMapper.findAll();
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherMapper.findByName(name);
    }

    @Override
    public void insert(Teacher teacher) {
        teacherMapper.insert(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherMapper.update(teacher);
    }

    @Override
    public void delete(Integer id) {
        teacherMapper.delete(id);
    }
}
