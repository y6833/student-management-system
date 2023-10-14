package com.yangy.service;

import com.yangy.entity.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> findAll();
    List<Teacher> findByName(String name);
    void insert(Teacher teacher);
    void update(Teacher teacher);
    void delete(Integer id);
}
