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
}
