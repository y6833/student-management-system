package com.yangy.mapper;

import com.yangy.pojo.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScoreMapper {
    @Select("SELECT * FROM tb_score")
    List<Score> findAll();

    @Select("SELECT * FROM tb_score WHERE student_id = #{studentId}")
    List<Score> findByStudentId(String studentId);

    @Select("SELECT * FROM tb_score WHERE course_id = #{courseId}")
    List<Score> findByCourseId(String courseId);

    @Insert("INSERT INTO tb_score(student_id, course_id, score, exam_date) VALUES(#{studentId}, #{courseId}, #{score}, #{examDate})")
    void insert(Score score);

    @Update("UPDATE tb_score SET score=#{score}, exam_date=#{examDate} WHERE id=#{id}")
    void update(Score score);

    @Delete("DELETE FROM tb_score WHERE id=#{id}")
    void delete(@Param("id") Integer id);
}
