package com.yangy.service;

import com.yangy.entity.Score;

import java.util.List;

public interface ScoreService {



    List<Score> findByStudentId(String studentId);


}
