package com.yangy.service;

import com.yangy.entity.QuestionTypes;

import java.util.List;

public interface QuestionTypesService {
    // 插入一条记录
    void insert(QuestionTypes record);
    // 根据id查询一条记录
    QuestionTypes selectByPrimaryKey(Integer id);
    // 查询所有记录
    List<QuestionTypes> selectAll();
    // 根据id更新一条记录
    void updateByPrimaryKey(QuestionTypes record);
    // 删除一条记录
    void deleteByPrimaryKey(Integer id);
}
