package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("select * from tb_teachers where id = #{id}")
    Teacher selectByRoleId(String id);

    @Delete("delete from tb_teachers where id = #{id}")
    boolean removeByTeaId(String id);

    @Select("select name from tb_teachers where id=#{uid}")
    String getNameById(String uid);

    @Select("select id from tb_teachers where name = #{value}")
    String getIdbyName(String value);

    @Select("select id from tb_teachers")
    List<String> getAllId();
}
