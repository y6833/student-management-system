package com.yangy.mapper;

import com.yangy.pojo.QuestionTypes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionTypesMapper {
    // 插入一条记录
    @Insert("INSERT INTO tb_questiontypes(description) VALUES(#{description})")
    void insert(QuestionTypes record);
    // 根据id查询一条记录
    @Select("SELECT * FROM tb_questiontypes WHERE id = #{id}")
    QuestionTypes selectByPrimaryKey(Integer id);
    // 查询所有记录
    @Select("SELECT * FROM tb_questiontypes")
    List<QuestionTypes> selectAll();
    // 根据id更新一条记录
    @Update("UPDATE tb_questiontypes SET description=#{description} WHERE id=#{id}")
    void updateByPrimaryKey(QuestionTypes record);
    // 删除一条记录
    @Delete("DELETE FROM tb_questiontypes WHERE id=#{id}")
    void deleteByPrimaryKey(Integer id);
}
