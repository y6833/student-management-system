package com.yangy.service.impl;

import com.yangy.mapper.ScoreMapper;
import com.yangy.pojo.Score;
import com.yangy.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> findAll() {
        return scoreMapper.findAll();
    }

    @Override
    public List<Score> findByStudentId(String studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

    @Override
    public List<Score> findByCourseId(String courseId) {
        return scoreMapper.findByCourseId(courseId);
    }

    @Override
    public void insert(Score score) {
        scoreMapper.insert(score);
    }

    @Override
    public void update(Score score) {
        scoreMapper.update(score);
    }

    @Override
    public void delete(Integer id) {
        scoreMapper.delete(id);
    }
}
