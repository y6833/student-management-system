package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Schedules;
import com.yangy.mapper.SchedulesMapper;
import com.yangy.service.ClassService;
import com.yangy.service.SchedulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SchedulesServiceImpl extends ServiceImpl<SchedulesMapper, Schedules> implements SchedulesService {

    @Resource
    private SchedulesMapper schedulesMapper;

    @Autowired
    private ClassService classService;

    @Override
    public IPage<Schedules> getPage(IPage<Schedules> page, QueryWrapper<Schedules> queryWrapper) {
            List<Schedules> schedulesList = page(page, queryWrapper).getRecords();
        for (Schedules schedules : schedulesList) {
            schedules.setClasss(classService.getClassName(schedules.getClasss()));
        }
            IPage<Schedules> schedulesIPage = page(page, queryWrapper).setRecords(schedulesList);
            return schedulesIPage;
    }

    @Override
    public boolean updataschedule(Schedules schedules) {
        return updateById(schedules);
    }

    @Override
    public boolean removeById(String id) {
        return schedulesMapper.removeById(id);
    }

    @Override
    public boolean saveschedules(Schedules schedules) {
        return save(schedules);
    }

    @Override
    public String getscheduleNameById(String id) {
        return schedulesMapper.getscheduleNameById(id);
    }

    @Override
    public List<String> getscheduleList() {
        return schedulesMapper.getscheduleList();
    }

    @Override
    public String getClassIdByName(String name) {
        return schedulesMapper.getClassIdByName(name);
    }

    @Override
    public String getscheduleIdByName(String name) {
        return schedulesMapper.getscheduleIdByName(name);
    }
}
