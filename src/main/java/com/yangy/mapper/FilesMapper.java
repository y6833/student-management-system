package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Files;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesMapper extends BaseMapper<Files> {

    @Select("select * from tb_file where md5 = #{md5}")
    Files getFileByMD5(String md5);


    @Select("select * from tb_file where url = #{fileURL}")
    Files findByURL(String fileURL);

    @Select("select type from tb_file")
    List<String> findFileTypeList();
}

