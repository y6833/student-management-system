package com.yangy.mapper;

import com.yangy.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("SELECT * FROM tb_students")
    List<Student> findAll();

    @Select("SELECT * FROM tb_students WHERE id = #{id}")
    List<Student> findByName(String name);

    @Insert("INSERT INTO tb_students(id,name,password, gender, birthday, grade, class_id, email, phone, address, major) VALUES(#{id},#{name},#{password}, #{gender}, #{birthday}, #{grade}, #{classId}, #{email}, #{phone}, #{address}, #{major})")
    void insert(Student student);

    @Update("UPDATE tb_students SET name=#{name},password=#{password}, gender=#{gender}, birthday=#{birthday}, grade=#{grade}, class_id=#{classId}, email=#{email}, phone=#{phone}, address=#{address}, major=#{major} WHERE id=#{id}")
    void update(Student student);

    @Delete("DELETE FROM tb_students WHERE id=#{id}")
    void delete(@Param("id") Integer id);
}
