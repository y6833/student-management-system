package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Student;
import org.apache.ibatis.annotations.Delete;

//@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Delete("delete from tb_students where id = #{id}")
    boolean removeByStuId(String id);
}
