package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Student;

import java.util.List;

public interface StudentService{
//    List<Student> selectPage(Integer pageNum, Integer pageSize);
    public boolean saveStudent(Student student);
    public List<Student> findAll();
    boolean removeById(Integer id);
    IPage<Student> getPage(IPage<Student> page, QueryWrapper<Student> queryWrapper);

    boolean updataStudent(Student student);

    boolean removeByStuId(String id);

    List<Student> findAllOver();

    void saveStudentList(List<Student> list);

    Student getStudentRoleId(String roleId);

    Student getStudentById(String id);
}
