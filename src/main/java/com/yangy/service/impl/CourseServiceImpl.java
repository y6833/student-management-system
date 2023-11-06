package com.yangy.service.impl;

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
}
