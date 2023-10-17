package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.StudentQuestionTypeScore;
import com.yangy.mapper.StudentQuestionTypeScoreMapper;
import com.yangy.service.StudentQuestionTypeScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentQuestionTypeScoreServiceImpl extends ServiceImpl<StudentQuestionTypeScoreMapper, StudentQuestionTypeScore> implements StudentQuestionTypeScoreService {
    @Resource
    private StudentQuestionTypeScoreMapper studentQuestionTypeScoreMapper;

}
