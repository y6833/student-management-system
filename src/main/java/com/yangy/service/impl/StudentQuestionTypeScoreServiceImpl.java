package com.yangy.service.impl;

import com.yangy.mapper.StudentQuestionTypeScoreMapper;
import com.yangy.entity.StudentQuestionTypeScore;
import com.yangy.service.StudentQuestionTypeScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentQuestionTypeScoreServiceImpl implements StudentQuestionTypeScoreService {
    @Autowired
    private StudentQuestionTypeScoreMapper studentQuestionTypeScoreMapper;

    @Override
    public void insert(StudentQuestionTypeScore record) {
        studentQuestionTypeScoreMapper.insert(record);
    }

    @Override
    public StudentQuestionTypeScore selectByPrimaryKey(Integer id) {
        return studentQuestionTypeScoreMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<StudentQuestionTypeScore> selectAll() {
        return studentQuestionTypeScoreMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(StudentQuestionTypeScore record) {
        studentQuestionTypeScoreMapper.updateByPrimaryKey(record);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        studentQuestionTypeScoreMapper.deleteByPrimaryKey(id);
    }
}
