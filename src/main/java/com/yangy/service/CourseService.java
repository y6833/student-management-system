package com.yangy.service;

import com.yangy.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course); // 添加课程

    void deleteCourseById(String courseId); // 根据课程编号删除课程

    void updateCourseById(Course course); // 根据课程编号更新课程

    Course getCourseById(String courseId); // 根据课程编号获取课程信息

    List<Course> getAllCourses(); // 获取所有课程信息
}
