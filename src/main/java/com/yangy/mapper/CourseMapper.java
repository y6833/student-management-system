package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select name from tb_courses where course_id=#{courseId}")
    String getCourseNameById(String courseId);

    @Select("Select course_id from tb_courses where name=#{courseName}")
    String getCourseIdByName(String courseName);

    @Select("select name from tb_courses")
    List<String> getSubjectList();
}
