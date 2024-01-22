package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Course;
import com.yangy.mapper.CourseMapper;
import com.yangy.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseMapper courseMapper; // mapper对象注入

    @Override
    public String getCourseNameById(String courseId) {
        return courseMapper.getCourseNameById(courseId);
    }

    @Override
    public String getCourseIdByName(String courseName) {
        return courseMapper.getCourseIdByName(courseName);
    }

    @Override
    public List<String> getSubjectList() {
        return courseMapper.getSubjectList();
    }

    @Override
    public Integer getSubjectMaxScore(String subject) {
        return courseMapper.getsubjectMaxScore(subject);
    }

    @Override
    public Integer getSubjectNameMaxScore(String subject) {
        return courseMapper.getSubjectNameMaxScore(subject);
    }

    @Override
    public IPage<Course> getPage(IPage<Course> page, QueryWrapper<Course> queryWrapper) {

        List<Course> courseList = page(page, queryWrapper).getRecords();
        IPage<Course> courseIPage = page(page, queryWrapper).setRecords(courseList);
        return courseIPage;
    }

    @Override
    public boolean saveCourse(Course course) {
        try {
            return save(course);

        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updatacourse(Course course) {
        return updateById(course);
    }

    @Override
    public boolean removeById(String id) {
        return courseMapper.removeById(id);
    }

    @Override
    public List<String> getCourseIdList() {
        return courseMapper.getCourseIdList();
    }

    @Override
    public String getTeacherById(String course) {
        return courseMapper.getTeacherById(course);
    }

}
