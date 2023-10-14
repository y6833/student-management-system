package com.yangy.service.impl;

import com.yangy.mapper.QuestionTypesMapper;
import com.yangy.entity.QuestionTypes;
import com.yangy.service.QuestionTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTypesServiceImpl implements QuestionTypesService {
    @Autowired
    private QuestionTypesMapper questionTypesMapper;

    @Override
    public void insert(QuestionTypes record) {
        questionTypesMapper.insert(record);
    }

    @Override
    public QuestionTypes selectByPrimaryKey(Integer id) {
        return questionTypesMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<QuestionTypes> selectAll() {
        return questionTypesMapper.selectAll();
    }

    @Override
    public void updateByPrimaryKey(QuestionTypes record) {
        questionTypesMapper.updateByPrimaryKey(record);
    }

    @Override
    public void deleteByPrimaryKey(Integer id) {
        questionTypesMapper.deleteByPrimaryKey(id);
    }
}
