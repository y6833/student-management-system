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
//通过学号查班级
    String getClassIdById(String studentId);
//通过学号查年级
    String getGradeById(String studentId);
//通过学号查专业
    String getMajorById(String studentId);

    Student getStudentByIdGM(String studentByScoreId);

    String getNameById(String uid);

    List<Student> getstudentListByGrade(String examGrade);

    List<Student> getstudentListByGradeAndMajor(String examGrade, String examMajor);
}
