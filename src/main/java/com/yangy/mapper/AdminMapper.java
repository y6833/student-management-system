package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("select * from tb_admin where id = #{id}")
    Admin selectByRoleId(String id);

    @Delete("delete from tb_admin where id = #{id}")
    boolean removeByTeaId(String id);

    @Select("select name from tb_admin where id=#{uid}")
    String getNameById(String uid);
}
