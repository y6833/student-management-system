package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Course;
import org.apache.ibatis.annotations.Delete;
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

    @Select("select fullmarks from  tb_courses where course_id = #{subject}")
    Integer getsubjectMaxScore(String subject);

    @Select("select fullmarks from  tb_courses where name = #{subject}")
    Integer getSubjectNameMaxScore(String subject);
    @Delete("delete from tb_courses where course_id = #{id}")
    boolean removeById(String id);

    @Select("select course_id from tb_courses")
    List<String> getCourseIdList();

    @Select("select teacher from tb_courses where course_id = #{courseId}")
    String getTeacherById(String courseId);

    @Select("select fullmarks from tb_courses where name = #{name}")
    Integer getFullmarksByName(String name);
    @Select("select fullmarks from tb_courses where course_id = #{id}")
    Integer getFullmarksById(String id);
}
