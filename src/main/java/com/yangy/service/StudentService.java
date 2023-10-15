package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Student;

import java.util.List;

public interface StudentService{
//    List<Student> findAll();
//    List<Student> findByName(String name);
//    int insert(Student student);
//    void update(Student student);
//    void delete(Integer id);
//    List<Student> selectPage(Integer pageNum, Integer pageSize);
//    Integer selectTotal();
//    List<Student> selectInPage(Map<String, String> stringStringMap);


    public boolean saveStudent(Student student);
    public List<Student> findAll();

    boolean removeById(Integer id);

    IPage<Student> getPage(IPage<Student> page, QueryWrapper<Student> queryWrapper);
}
