package com.yangy.service;

import com.yangy.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    List<Student> findByName(String name);
    void insert(Student student);
    void update(Student student);
    void delete(Integer id);
}
