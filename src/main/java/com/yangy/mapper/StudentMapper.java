package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Student;

//@Mapper
public interface StudentMapper extends BaseMapper<Student> {
//    @Select("SELECT * FROM tb_students")
//    List<Student> findAll();
//
//    @Select("SELECT * FROM tb_students WHERE id = #{id}")
//    List<Student> findByName(String name);
//
//    @Insert("INSERT INTO tb_students(id,name,password, gender, birthday, grade, class_id, email, phone, address, major) VALUES(#{id},#{name},#{password}, #{gender}, #{birthday}, #{grade}, #{classId}, #{email}, #{phone}, #{address}, #{major})")
//    int insert(Student student);
//
//    @Update("UPDATE tb_students SET name=#{name},password=#{password}, gender=#{gender}, birthday=#{birthday}, grade=#{grade}, class_id=#{classId}, email=#{email}, phone=#{phone}, address=#{address}, major=#{major} WHERE id=#{id}")
//    void update(Student student);
//
//    @Delete("DELETE FROM tb_students WHERE id=#{id}")
//    void delete(@Param("id") Integer id);
//
//    @Select("select * from tb_students limit #{pageNum}, #{pageSize}")
//    List<Student> selectPage(Integer pageNum, Integer pageSize);
//
//
//    @Select("select count(*) from tb_students")
//    Integer selectTotal();
//
//    List<Student> selectInPage(Map<String, String> stringStringMap);
}
