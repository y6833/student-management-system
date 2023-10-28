package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Teacher;
import com.yangy.entity.User;
import com.yangy.mapper.TeacherMapper;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.TeacherService;
import com.yangy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private UserService userService;

    @Override
    public Teacher getTeacherByRoleId(String roleId) {
        return teacherMapper.selectByRoleId(roleId);
    }

    @Override
    public void saveTeacherList(List<Teacher> list) {
        //专业id和name转换
        for (Teacher teacher : list) {
            teacher.setClassId(classService.getIdByclassName(teacher.getClassId()));
            User user = new User(teacher.getId(),teacher.getPassword(), 1,teacher.getId(),3,"");
            userService.addUser(user);
        }
        //将导入的学生创建user账户

        saveBatch(list);
    }

    @Override
    public List<Teacher> findAllOver() {
        List<Teacher> teachers = list();
        for (Teacher teacher : teachers) {
            teacher.setClassId(classService.getClassName(teacher.getClassId()));
        }
        return teachers;
    }

    @Override
    public IPage<Teacher> getPage(IPage<Teacher> page, QueryWrapper<Teacher> queryWrapper) {
        List<Teacher> teacherList = page(page, queryWrapper).getRecords();

        for (Teacher teacher : teacherList) {
            teacher.setClassId(classService.getClassName(teacher.getClassId()));
        }
        IPage<Teacher> teacherIPage = page(page, queryWrapper).setRecords(teacherList);
        return teacherIPage;
    }

    @Override
    public boolean saveTeacher(Teacher teacher) {
        teacher.setClassId(classService.getIdByclassName(teacher.getClassId()));
        return save(teacher);
    }

    @Override
    public boolean updataTeacher(Teacher teacher) {
        teacher.setClassId(classService.getIdByclassName(teacher.getClassId()));
        return updateById(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return list();
    }

    @Override
    public boolean removeByTeaId(String id) {
        return teacherMapper.removeByTeaId(id);
    }
}
