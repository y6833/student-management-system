package com.yangy.mapper;

import com.yangy.entity.Major;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MajorMapper {
    @Insert("INSERT INTO tb_major(major_id, major_name, credit) VALUES(#{major_id}, #{major_name}, #{credit})")
    void insert(Major major); // 插入一条专业记录

    @Delete("DELETE FROM tb_major WHERE id = #{id}")
    void deleteById(@Param("id") String id); // 根据ID删除一条专业记录

    @Update("UPDATE tb_major SET major_name=#{major_name}, credit=#{credit} WHERE id=#{id}")
    void updateById(Major major); // 根据ID更新一条专业记录

    @Select("SELECT * FROM tb_major WHERE id = #{id}")
    Major selectById(@Param("id") String id); // 根据ID查询一条专业记录

    @Select("SELECT * FROM tb_major")
    List<Major> selectAll(); // 查询所有专业记录
}
