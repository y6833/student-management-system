package com.yangy.service;

import com.yangy.entity.ScheduleDTO;

import java.util.List;

public interface ScheduleService {

    boolean saveschedule(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> getscheduleWorkList(String id);

    boolean updatascheduleInFo(ScheduleDTO scheduleDTO);

    boolean deleteScheduleInFo(ScheduleDTO scheduleDTO);

    List<String> getCourseListById(String scheduleId);
}
