package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Course;

import java.util.List;

public interface CourseService {

    String getCourseNameById(String courseId);

    String getCourseIdByName(String courseName);

    List<String> getSubjectList();

    Integer getSubjectMaxScore(String subject);

    Integer getSubjectNameMaxScore(String subject);

    IPage<Course> getPage(IPage<Course> page, QueryWrapper<Course> queryWrapper);

    boolean saveCourse(Course course);


    boolean updatacourse(Course course);

    boolean removeById(String id);

    List<String> getCourseIdList();

    String getTeacherById(String course);

    Integer getFullmarksByName(String name);

    Integer getFullmarksById(String s);
}
