package com.yangy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangy.entity.Schedule;
import com.yangy.entity.ScheduleDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScheduleMapper extends BaseMapper<Schedule> {

    @Select("select * from tb_schedule where schedule_id=#{id} ORDER BY weekly")
    List<Schedule> getscheduleWorkList(String id);

    @Delete("delete from tb_schedule where schedule_id=#{scheduleId} and week = #{week} and section=#{section} and course=#{course} and classroom=#{classroom}")
    boolean remove(ScheduleDTO scheduleDTO);

    @Select("select DISTINCT course from tb_schedule where schedule_id=#{scheduleId}")
    List<String> getCourseListById(String scheduleId);
}
