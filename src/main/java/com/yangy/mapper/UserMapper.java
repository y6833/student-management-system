package com.yangy.mapper;

import com.yangy.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO tb_user(username, password,activation, role_id, role_name) VALUES(#{username}, #{password},#{activation}, #{roleId}, #{roleName})")
    void insert(User user);

    @Delete("DELETE FROM tb_user WHERE id = #{id}")
    void deleteById(@Param("id") String id);

    @Update("UPDATE tb_user SET username=#{username}, password=#{password},activation=#{activation}, role_id=#{roleId}, role_name=#{roleName} WHERE id=#{id}")
    void updateById(User user);

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
}
