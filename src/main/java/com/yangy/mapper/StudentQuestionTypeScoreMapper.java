package com.yangy.mapper;

import com.yangy.entity.StudentQuestionTypeScore;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentQuestionTypeScoreMapper {
    // 插入一条记录
    @Insert("INSERT INTO tb_StudentQuestionTypeScore(student_id, course_id, question_type_id, score, exam_time) VALUES(#{studentId}, #{courseId}, #{questionTypeId}, #{score}, #{examTime})")
    int insert(StudentQuestionTypeScore record);
    // 根据id查询一条记录
    @Select("SELECT * FROM tb_StudentQuestionTypeScore WHERE id = #{id}")
    StudentQuestionTypeScore selectByPrimaryKey(Integer id);
    // 查询所有记录
    @Select("SELECT * FROM tb_StudentQuestionTypeScore")
    List<StudentQuestionTypeScore> selectAll();
    // 根据id更新一条记录
    @Update("UPDATE tb_StudentQuestionTypeScore SET student_id=#{studentId}, course_id=#{courseId}, question_type_id=#{questionTypeId}, score=#{score}, exam_time=#{examTime} WHERE id=#{id}")
    int updateByPrimaryKey(StudentQuestionTypeScore record);
    // 删除一条记录
    @Delete("DELETE FROM tb_StudentQuestionTypeScore WHERE id=#{id}")
    int deleteByPrimaryKey(Integer id);
}
