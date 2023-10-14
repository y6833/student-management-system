package com.yangy.mapper;

import com.yangy.entity.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionMapper {
    @Insert("INSERT INTO tb_permission(action,annotation) VALUES(#{action}, #{annotation})")
    void insert(Permission permission); // 插入一条权限记录

    @Delete("DELETE FROM tb_permission WHERE id = #{id}")
    void deleteById(@Param("id") Integer id); // 根据ID删除一条权限记录

    @Update("UPDATE tb_permission SET action=#{action},annotation=#{annotation} WHERE id=#{id}")
    void updateById(Permission permission); // 根据ID更新一条权限记录

    @Select("SELECT * FROM tb_permission WHERE id = #{id}")
    Permission selectById(@Param("id") Integer id); // 根据ID查询一条权限记录

    @Select("SELECT * FROM tb_permission")
    List<Permission> selectAll(); // 查询所有权限记录
}
