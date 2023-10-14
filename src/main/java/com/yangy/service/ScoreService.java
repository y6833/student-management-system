package com.yangy.service;

import com.yangy.entity.Score;

import java.util.List;

public interface ScoreService {

    List<Score> findAll();

    List<Score> findByStudentId(String studentId);

    List<Score> findByCourseId(String courseId);

    void insert(Score score);

    void update(Score score);

    void delete(Integer id);
}
