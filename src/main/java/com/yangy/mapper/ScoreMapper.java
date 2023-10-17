package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Score;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface ScoreMapper extends BaseMapper<Score> {

    @Select("SELECT * FROM tb_score WHERE student_id = #{studentId}")
    List<Score> findByStudentId(String studentId);

}
