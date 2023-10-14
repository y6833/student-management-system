package com.yangy.mapper;

import com.yangy.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    @Select("SELECT * FROM tb_teachers")
    List<Teacher> findAll();

    @Select("SELECT * FROM tb_teachers WHERE name = #{name}")
    List<Teacher> findByName(String name);

    @Insert("INSERT INTO tb_teachers(id,name,password, gender, birthday, grade, class_id, email, phone, address) VALUES(#{id},#{name},#{password}, #{gender}, #{birthday}, #{grade}, #{classId}, #{email}, #{phone}, #{address})")
    void insert(Teacher teacher);

    @Update("UPDATE tb_teachers SET id=#{id},name=#{name},password=#{password}, gender=#{gender}, birthday=#{birthday}, grade=#{grade}, class_id=#{classId}, email=#{email}, phone=#{phone}, address=#{address} WHERE id=#{id}")
    void update(Teacher teacher);

    @Delete("DELETE FROM tb_teachers WHERE id=#{id}")
    void delete(@Param("id") Integer id);
}
