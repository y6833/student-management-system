package com.yangy.mapper;

import com.yangy.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Insert("INSERT INTO tb_admin (id,username,password, gender, email, phone, birthday) VALUES (#{id}, #{username},#{password}, #{gender}, #{email}, #{phone}, #{birthday})")
    void addAdmin(Admin admin);

    @Delete("DELETE FROM tb_admin WHERE id = #{id}")
    void deleteAdminById(@Param("id") String id);

    @Update("UPDATE tb_admin SET username = #{username},password=#{password}, gender = #{gender}, birthday = #{birthday}, email = #{email}, phone = #{phone} WHERE id = #{id}")
    void updateAdminById(Admin admin);

    @Select("SELECT * FROM tb_admin WHERE id = #{id}")
    Admin getAdminById(@Param("id") String id);

    @Select("SELECT * FROM tb_admin")
    List<Admin> getAllAdmins();
}
