package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Examination;

import java.sql.Date;
import java.util.List;

public interface ExaminationService {
    Date getDateByName(String examName);

    IPage<Examination> getPage(IPage<Examination> page, QueryWrapper<Examination> queryWrapper);

    boolean addExamination(Examination examination);

    boolean createScoreTable(Examination examination, List<String> courses);

    boolean deleteById(String id);

    boolean updataExam(Examination examination);

    List<Examination> findAllOver();

    void saveExaminationList(List<Examination> list);

    boolean deleteTable(String id);
}
