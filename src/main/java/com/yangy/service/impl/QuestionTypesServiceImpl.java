package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.QuestionTypes;
import com.yangy.mapper.QuestionTypesMapper;
import com.yangy.service.QuestionTypesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QuestionTypesServiceImpl extends ServiceImpl<QuestionTypesMapper, QuestionTypes> implements QuestionTypesService {
    @Resource
    private QuestionTypesMapper questionTypesMapper;

}
