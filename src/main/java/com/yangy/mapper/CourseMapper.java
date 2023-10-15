package com.yangy.mapper;

import com.yangy.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface CourseMapper {
    @Insert("INSERT INTO tb_courses(course_id,name,teacher,teacher_id,remark) VALUES(#{courseId},#{name},#{teacher},#{teacherId},#{remark})")
    void insert(Course course); // 插入一条课程记录

    @Delete("DELETE FROM tb_courses WHERE course_id = #{courseId}")
    void deleteById(@Param("courseId") String courseId); // 根据课程编号删除一条课程记录

    @Update("UPDATE tb_courses SET name=#{name},teacher=#{teacher},teacher_id=#{teacherId},remark=#{remark} WHERE course_id=#{courseId}")
    void updateById(Course course); // 根据课程编号更新一条课程记录

    @Select("SELECT * FROM tb_courses WHERE course_id = #{courseId}")
    Course selectById(@Param("courseId") String courseId); // 根据课程编号查询一条课程记录

    @Select("SELECT * FROM tb_courses")
    List<Course> selectAll(); // 查询所有课程记录
}
