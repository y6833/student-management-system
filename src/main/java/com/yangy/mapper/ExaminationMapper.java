package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Examination;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;
import java.util.List;

public interface ExaminationMapper extends BaseMapper<Examination> {

    @Select("select exam_date from tb_examination where exam_name = #{examName};")
    Date getDateByName(String examName);

    @Update("update ts_examination set exam_name = #{examName}, exam_major = #{examMajor},exam_grade = #{examGrade},exam_date=#{examDate} where id = #{id}")
    boolean updataExam(Examination examination);


    @Select("select * from ts_examination")
    List<Examination> getAll();

    @Select("select exam_date from ts_examination where id=#{id}")
    Date getDateByExamId(String id);

    @Select("select exam_name from ts_examination where id=#{id}")
    String getDateNameByExamId(String id);

    @Select("select id from ts_examination where exam_name = #{examName} and exam_date = #{examDate}")
    String getIdByNameAndDate(String examName, java.util.Date examDate);

    @Select("select id from ts_examination where exam_name = #{examName}")
    String getIdByExamName(String examName);

    @Select("select * from ts_examination where id=#{examId}")
    Examination getExamById(String examId);

    @Select("select id from ts_examination where exam_name = #{examName} And exam_grade=#{gradeValue}")
    String getIdByExamNameAndGrade(String examName, String gradeValue);

    @Select("select id from ts_examination where exam_name = #{examValue} And exam_grade=#{gradeValue} AND exam_major=#{major}")
    String getIdByExamNameAndGradeAndMajorId(String examValue, String gradeValue, String major);

    @Select("select exam_name from ts_examination where schedule_name != '测试' AND schedule_name IS NOT NULL")
    List<String> getExamListks();
    @Select("select exam_name from ts_examination where exam_major =#{majorId} AND exam_grade=#{gradeId} AND schedule_name != '测试' AND schedule_name IS NOT NULL order by 'exam_data' ")
    List<String> etExamListksByGradeIdAndMajorId(String gradeId, String majorId);

    @Select("select exam_name from ts_examination where schedule_name = #{scheduleName}")
    List<String> getEXamNameListByScheduleName(String scheduleName);

    @Select("select schedule_name from ts_examination where exam_name = #{examValue}")
    String getScheduleNameByExamName(String examValue);

    @Select("select * from ts_examination where schedule_name = #{scheduleName}")
    List<Examination> getEXamListByScheduleName(String scheduleName);


    @Select("select * from ts_examination where exam_major =#{majorId} AND exam_grade=#{gradeId} AND schedule_name = '测试' AND schedule_name IS NOT NULL order by 'exam_data' ")
    List<Examination> getExamByGradeIdAndMajorId(String gradeId, String majorId);
}
