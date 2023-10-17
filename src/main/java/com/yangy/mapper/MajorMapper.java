package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Major;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface MajorMapper extends BaseMapper<Major> {

    @Select("select major_name from tb_major where major_id = #{major}")
    String getMajorName(String major);

    @Select("select distinct major_name from tb_major")
    List<String> getMajorList();

    @Select("select major_id from tb_major where major_name =#{value} ;")
    String getIdByclassName(String value);
}
