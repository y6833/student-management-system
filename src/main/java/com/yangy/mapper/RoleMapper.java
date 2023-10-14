package com.yangy.mapper;

import com.yangy.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Insert("INSERT INTO tb_role(name) VALUES(#{name})")
    void insert(Role role); // 插入一条角色记录

    @Delete("DELETE FROM tb_role WHERE id = #{id}")
    void deleteById(@Param("id") Integer id); // 根据ID删除一条角色记录

    @Update("UPDATE tb_role SET name=#{name} WHERE id=#{id}")
    void updateById(Role role); // 根据ID更新一条角色记录

    @Select("SELECT * FROM tb_role WHERE id = #{id}")
    Role selectById(@Param("id") Integer id); // 根据ID查询一条角色记录

    @Select("SELECT * FROM tb_role")
    List<Role> selectAll(); // 查询所有角色记录
}
