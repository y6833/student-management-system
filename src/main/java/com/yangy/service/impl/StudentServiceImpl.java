package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Student;
import com.yangy.mapper.StudentMapper;
import com.yangy.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
//    @Autowired
//    private StudentMapper studentMapper;
//    @Autowired
//    private ClassService classService;
//    @Autowired
//    private MajorService majorService;
//
//    @Override
//    public List<Student> findAll() {
//        return studentMapper.findAll();
//    }
//
//    @Override
//    public List<Student> findByName(String name) {
//        return studentMapper.findByName(name);
//    }
//
//    @Override
//    public int insert(Student student) {
//        studentMapper.insert(student);
//    }
//
//    @Override
//    public void update(Student student) {
//        studentMapper.update(student);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        studentMapper.delete(id);
//    }
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
//        if(student.getId() == null){
//            return save(student); //mybatis-plus提供的插入方法
//        } else {
//            return updateById(student);
//        }
        return saveOrUpdate(student);
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
        return page(page,queryWrapper);
    }


}
