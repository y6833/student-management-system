package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Schedules;

import java.util.List;

public interface SchedulesService {
    IPage<Schedules> getPage(IPage<Schedules> page, QueryWrapper<Schedules> queryWrapper);

    boolean updataschedule(Schedules schedules);

    boolean removeById(String id);

    boolean saveschedules(Schedules schedules);

    String getscheduleNameById(String id);

    List<String> getscheduleList();

    String getClassIdByName(String name);

    String getscheduleIdByName(String name);

    String getscheduleNameByClassIdAndLevelAndSemester(String classId, Integer level, String semester);

    String getscheduleNameByClassIdAndLevelAndSemester1(String classId, Integer level, Integer semester);
}
