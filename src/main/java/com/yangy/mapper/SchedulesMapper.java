package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Schedules;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SchedulesMapper  extends BaseMapper<Schedules> {
    @Delete("delete from tb_schedules where id = #{id}")
    boolean removeById(String id);

    @Select("select name from tb_schedules where id = #{id}")
    String getscheduleNameById(String id);

    @Select("select name from tb_schedules")
    List<String> getscheduleList();

    @Select("select classs from tb_schedules where name = #{name}")
    String getClassIdByName(String name);

    @Select("select id from tb_schedules where name = #{name}")
    String getscheduleIdByName(String name);

    @Select("select name from tb_schedules where classs=#{classId} AND grade = #{level} AND stage = #{semester}")
    String getscheduleNameByClassIdAndLevelAndSemester(String classId, Integer level, String semester);
}
