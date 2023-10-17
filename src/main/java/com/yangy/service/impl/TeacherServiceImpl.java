package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Teacher;
import com.yangy.mapper.TeacherMapper;
import com.yangy.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

}
