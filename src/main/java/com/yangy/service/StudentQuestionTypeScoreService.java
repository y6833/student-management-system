package com.yangy.service;

import com.yangy.entity.StudentQuestionTypeScore;

import java.util.List;

public interface StudentQuestionTypeScoreService {
    // 插入一条记录
    void insert(StudentQuestionTypeScore record);
    // 根据id查询一条记录
    StudentQuestionTypeScore selectByPrimaryKey(Integer id);
    // 查询所有记录
    List<StudentQuestionTypeScore> selectAll();
    // 根据id更新一条记录
    void updateByPrimaryKey(StudentQuestionTypeScore record);
    // 删除一条记录
    void deleteByPrimaryKey(Integer id);
}
