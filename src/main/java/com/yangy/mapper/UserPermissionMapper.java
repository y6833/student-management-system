package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.UserPermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface UserPermissionMapper extends BaseMapper<UserPermission> {
    @Select("SELECT DISTINCT user_id FROM tb_user_permission")
    List<String> getAllUserId();

    @Select("select permission_id from tb_user_permission where user_id = #{uid}")
    List<Integer> getUserPermissionList(String uid);

    @Insert("insert into tb_user_permission(user_id,permission_id) values(#{userId},#{permissionId})")
    boolean addUserPermission(String userId, int permissionId);

    @Delete("delete from tb_user_permission where user_id = #{userId} and permission_id = #{permissionId}")
    boolean deletePermission(String userId, int permissionId);
}
