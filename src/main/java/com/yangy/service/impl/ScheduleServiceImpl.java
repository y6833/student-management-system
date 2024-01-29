package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Schedule;
import com.yangy.entity.ScheduleDTO;
import com.yangy.mapper.ScheduleMapper;
import com.yangy.service.CourseService;
import com.yangy.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {

    @Resource
    private ScheduleMapper scheduleMapper;

    @Autowired
    private CourseService courseService;

    @Override
    public boolean saveschedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        for (Integer w : scheduleDTO.getWeekly()) {
            schedule = new Schedule();
            schedule.setScheduleId(scheduleDTO.getScheduleId());
            schedule.setWeek(scheduleDTO.getWeek());
            schedule.setSection(scheduleDTO.getSection());
            schedule.setCourse(scheduleDTO.getCourse());
            schedule.setClassroom(scheduleDTO.getClassroom());
            schedule.setWeekly(w);
            boolean s = save(schedule);
            if(s == false){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ScheduleDTO> getscheduleWorkList(String id) {

        List<Schedule> scheduleList = scheduleMapper.getscheduleWorkList(id);
        Map<String, String> resultMap = new HashMap<>();
        for (Schedule schedule : scheduleList) {
            String key = schedule.getSection() + "_" + schedule.getWeek() + "_" + schedule.getCourse() + "_" + schedule.getClassroom();
            resultMap.put(key, resultMap.getOrDefault(key, "") + schedule.getWeekly() + ",");
        }

        List<ScheduleDTO> resultList = new ArrayList<>();
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            String[] parts = entry.getKey().split("_");
            int section = Integer.parseInt(parts[0]);
            int week = Integer.parseInt(parts[1]);
            String course = parts[2];
            String classroom = parts[3];
            String value = entry.getValue();
            List<Integer> intList = Arrays.stream(value.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            ArrayList<Integer> weekly = new ArrayList<>(intList);
            String teacher = courseService.getTeacherById(course);
            String courseName = courseService.getCourseNameById(course);
            Integer type = courseService.getTypeById(course);
            resultList.add(new ScheduleDTO(id,weekly,week,section,courseName,classroom,teacher,type));
        }
        return resultList;
    }

    @Override
    public boolean updatascheduleInFo(ScheduleDTO scheduleDTO) {
        //删除这个课表，每周这周几的第几节课
        boolean remove = scheduleMapper.remove(scheduleDTO);

//        boolean remove = scheduleMapper.update();
        if(scheduleDTO.getWeekly().size()>0){
            return saveschedule(scheduleDTO);
        }
        return remove;
    }

    @Override
    public boolean deleteScheduleInFo(ScheduleDTO scheduleDTO) {
        scheduleDTO.setCourse(courseService.getCourseIdByName(scheduleDTO.getCourse()));
        return scheduleMapper.remove(scheduleDTO);
    }

    @Override
    public List<String> getCourseListById(String scheduleId) {
        List<String> courseListById = scheduleMapper.getCourseListById(scheduleId);
        List<String> courseNameList = new ArrayList<>();
        String courseName = new String();
        for (String s : courseListById) {
            courseName = courseService.getCourseNameById(s);
            courseNameList.add(courseName);
        }
        return courseNameList;
    }
}
