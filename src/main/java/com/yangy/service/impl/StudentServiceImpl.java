package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Student;
import com.yangy.entity.User;
import com.yangy.mapper.StudentMapper;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.StudentService;
import com.yangy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private UserService userService;

    @Resource
    private StudentMapper studentMapper;
//
//
//    @Override
//    public List<Student> selectPage(Integer pageNum, Integer pageSize) {
//        List<Student> students = studentMapper.selectPage(pageNum, pageSize);
//        for (Student student : students) {
//            student.setClassId(classService.getClassName(student.getClassId()));
//            student.setMajor(majorService.getMajorName(student.getMajor()));
//        }
//        return students;
//    }
//
//    @Override
//    public Integer selectTotal() {
//        return studentMapper.selectTotal();
//    }
//
//    @Override
//    public List<Student> selectInPage(Map<String, String> stringStringMap) {
//        return studentMapper.selectInPage(stringStringMap);
//    }

    @Override
    public boolean saveStudent(Student student){
        student.setClassId(classService.getIdByclassName(student.getClassId()));
        student.setMajor(majorService.getIdByclassName(student.getMajor()));
//        if(student.getId() == null){
//            return save(student); //mybatis-plus提供的插入方法
//        } else {
//            return updateById(student);
//        }
        return save(student);
//        return saveOrUpdate(student);
    }

    @Override
    public boolean updataStudent(Student student) {
        student.setClassId(classService.getIdByclassName(student.getClassId()));
        student.setMajor(majorService.getIdByclassName(student.getMajor()));
        return updateById(student);
    }

    @Override
    public boolean removeByStuId(String id) {
        return studentMapper.removeByStuId(id);
    }

    @Override
    public List<Student> findAllOver() {
        List<Student> students = list();
        for (Student student : students) {
            student.setClassId(classService.getClassName(student.getClassId()));
            student.setMajor(majorService.getMajorName(student.getMajor()));
        }
        return students;
    }

    @Override
    public void saveStudentList(List<Student> list) {
        //专业id和name转换
        for (Student student : list) {
            student.setClassId(classService.getIdByclassName(student.getClassId()));
            student.setMajor(majorService.getIdByclassName(student.getMajor()));
            User user = new User(student.getId(),student.getPassword(), 1,student.getId(),4,"");
            userService.addUser(user);
        }
        //将导入的学生创建user账户

        saveBatch(list);
    }

    @Override
    public Student getStudentRoleId(String roleId) {
        return studentMapper.selectByRoleId(roleId);
    }

    @Override
    public Student getStudentById(String id) {
        return studentMapper.selectById(id);
    }

    @Override
    public String getClassIdById(String studentId) {
        return studentMapper.getClassIdById(studentId);
    }

    @Override
    public String getGradeById(String studentId) {
        return studentMapper.getGradeById(studentId);
    }

    @Override
    public String getMajorById(String studentId) {
        return studentMapper.getMajorById(studentId);
    }

    @Override
    public List<Student> findAll(){
        return list();
    }

    @Override
    public boolean removeById(Integer id) {
        return removeById(id);
    }

    @Override
    public IPage<Student> getPage(IPage<Student> page, QueryWrapper<Student> queryWrapper) {

        List<Student> studentList = page(page, queryWrapper).getRecords();

        for (Student student : studentList) {
            student.setClassId(classService.getClassName(student.getClassId()));
            student.setMajor(majorService.getMajorName(student.getMajor()));
        }
        IPage<Student> studentIPage = page(page, queryWrapper).setRecords(studentList);
        return studentIPage;
    }




}
