package com.yangy.mapper;

import com.yangy.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO tb_user(username, password,activation, role_id, role_name) VALUES(#{username}, #{password},#{activation}, #{roleId}, #{roleName})")
    Boolean insert(User user);

    @Delete("DELETE FROM tb_user WHERE id = #{id}")
    void deleteById(@Param("id") String id);

    @Update("UPDATE tb_user SET username=#{username}, password=#{password},activation=#{activation}, role_id=#{roleId}, role_name=#{roleName},avatar = #{avatar} WHERE id=#{id}")
    Boolean updateById(User user);

    @Select("SELECT * FROM tb_user WHERE id = #{id}")
    User selectById(@Param("id") Integer id);

    @Select("SELECT * FROM tb_user")
    List<User> selectAll();

    @Select("SELECT * FROM tb_user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("select * from tb_user limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize);

    @Select("select count(*) from tb_user")
    Integer selectTotal();


    @Delete("delete from tb_user where username = #{username}")
    boolean removeByUsername(String username);

    @Select("SELECT * FROM tb_user WHERE role_id = #{roleId}")
    User selectByRoleId(String roleId);

    @Update("update tb_user set activation = #{isActivate} where role_id = #{roleId}")
    boolean updateIsActivate(String roleId, Integer isActivate);

    @Select("SELECT avatar FROM tb_user WHERE username = #{username}")
    String getAvatarURL(String username);

    @Select("select role_name from tb_user where role_id=#{uid}")
    int getRoleNamebyRoleId(String uid);

    @Select("select role_id from tb_user where username = #{username}")
    String getRoleIdByUsername(String username);
}
