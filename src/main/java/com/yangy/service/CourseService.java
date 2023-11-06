package com.yangy.service;

import java.util.List;

public interface CourseService {

    String getCourseNameById(String courseId);

    String getCourseIdByName(String courseName);

    List<String> getSubjectList();

    Integer getSubjectMaxScore(String subject);

    Integer getSubjectNameMaxScore(String subject);
}
