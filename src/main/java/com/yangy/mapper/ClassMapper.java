package com.yangy.mapper;

import com.yangy.entity.Tclass;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface ClassMapper {


    @Insert("INSERT INTO tb_class(class_id,class_name,grade_id,major_id,head_teacher_id) VALUES(#{classId},#{className},#{gradeId},#{majorId},#{headTeacherId})")
    void insert(Tclass tclass); // 插入一条班级记录

    @Delete("DELETE FROM tb_class WHERE class_id = #{id}")
    void deleteById(@Param("id") String id); // 根据ID删除一条班级记录

    @Update("UPDATE tb_class SET class_id=#{classId},class_name=#{className},grade_id=#{gradeId},major_id=#{majorId},head_teacher_id=#{headTeacherId} WHERE tclass_id=#{id}")
    void updateById(Tclass tclass); // 根据ID更新一条班级记录

    @Select("SELECT * FROM tb_class WHERE class_id = #{id}")
    Class selectById(@Param("id") String id); // 根据ID查询一条班级记录

    @Select("SELECT * FROM tb_class")
    List<Class> selectAll(); // 查询所有班级记录

    @Select("select class_name from tb_class WHERE class_id = #{id}")
    String getClassName(String id);
}
