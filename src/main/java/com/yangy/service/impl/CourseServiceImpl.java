package com.yangy.service.impl;

import com.yangy.mapper.CourseMapper;
import com.yangy.entity.Course;
import com.yangy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper; // mapper对象注入

    @Override
    public void addCourse(Course course) { // 添加课程
        courseMapper.insert(course);
    }

    @Override
    public void deleteCourseById(String courseId) { // 根据课程编号删除课程
        courseMapper.deleteById(courseId);
    }

    @Override
    public void updateCourseById(Course course) { // 根据课程编号更新课程
        courseMapper.updateById(course);
    }

    @Override
    public Course getCourseById(String courseId) { // 根据课程编号获取课程信息
        return courseMapper.selectById(courseId);
    }

    @Override
    public List<Course> getAllCourses() { // 获取所有课程信息
        return courseMapper.selectAll();
    }
}
