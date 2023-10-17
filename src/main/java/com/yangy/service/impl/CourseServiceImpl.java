package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Course;
import com.yangy.mapper.CourseMapper;
import com.yangy.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private CourseMapper courseMapper; // mapper对象注入

}
