package com.yangy.mapper;

import com.yangy.entity.UserPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPermissionMapper {
    @Insert("INSERT INTO tb_user_permission(user_id,permission_id) VALUES(#{userId},#{permissionId})")
    void insert(UserPermission userPermission); // 插入一条用户权限记录

    @Delete("DELETE FROM tb_user_permission WHERE id = #{id}")
    void deleteById(@Param("id") Integer id); // 根据ID删除一条用户权限记录

    @Update("UPDATE tb_user_permission SET user_id=#{userId},permission_id=#{permissionId} WHERE id=#{id}")
    void updateById(UserPermission userPermission); // 根据ID更新一条用户权限记录

    @Select("SELECT * FROM tb_user_permission WHERE id = #{id}")
    UserPermission selectById(@Param("id") Integer id); // 根据ID查询一条用户权限记录

    @Select("SELECT * FROM tb_user_permission")
    List<UserPermission> selectAll(); // 查询所有用户权限记录
}
