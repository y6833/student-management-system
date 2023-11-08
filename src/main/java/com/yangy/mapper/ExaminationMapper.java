package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Examination;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;

public interface ExaminationMapper extends BaseMapper<Examination> {

    @Select("select exam_date from tb_examination where exam_name = #{examName};")
    Date getDateByName(String examName);

    @Update("update ts_examination set exam_name = #{examName}, exam_major = #{examMajor},exam_grade = #{examGrade},exam_date=#{examDate} where id = #{id}")
    boolean updataExam(Examination examination);
}
