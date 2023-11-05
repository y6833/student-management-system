package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Examination;
import com.yangy.entity.Score;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

//@Mapper
public interface ScoreMapper extends BaseMapper<Score> {



    @Select("SELECT * FROM tb_score WHERE student_id = #{studentId}")
    List<Score> findByStudentId(String studentId);

    @Select("select * from tb_examination")
    List<Examination> getExamTimes();

    @Select("SELECT * FROM tb_score WHERE student_id = #{id} AND exam_date=#{examDate}")
    List<Score> findScoreByStuIdAndExamDate(String id, Date examDate);

    @Select("SELECT RANK() OVER (PARTITION BY exam_date ORDER BY sum(score) DESC) AS rank FROM scores;")
    Integer getClassRanking(String id, String classId, Date examDate);

    @Update("update tb_score set score = #{scores} where student_id = #{id} AND course_id = #{courseid} AND exam_date = #{examDate}")
    boolean updataScore(String id, String courseid, Double scores,Date examDate);

    @Select("SELECT exam_name from tb_examination")
    List<String> getExamList();

    @Insert("insert into tb_score(student_id,course_id,score,exam_date) values(#{studentId},#{courseId},#{score},#{examDate})")
    boolean addStudentScore(Score score1);
}
