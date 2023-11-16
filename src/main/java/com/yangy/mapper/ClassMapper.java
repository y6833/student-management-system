package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Tclass;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//@Mapper
public interface ClassMapper extends BaseMapper<Tclass> {

    @Select("select class_name from tb_class WHERE class_id = #{id}")
    String getClassName(String id);

    @Select("select DISTINCT class_name from tb_class")
    List<String> getClassList();


    @Select("SELECT DISTINCT grade_id FROM tb_class")
    List<String> getGradeList();

    @Select("select class_id from tb_class where class_name =#{value}")
    String getIdByclassName(String value);

    @Select("select class_name from tb_class where grade_id = #{gradeId}")
    List<String> getClassListBygradeId(String gradeId);


    //通过班级获得专业
    @Select("select major_id from tb_class where class_id = #{classId}")
    String getMajorByclassId(String classId);

    //通过班级获得年级
    @Select("SELECT grade_id FROM tb_class where class_id = #{classId}")
    List<String> getGradeByclassId(String classId);

    @Delete("delete from tb_class where class_id = #{id}")
    boolean removeclassById(String id);
}
