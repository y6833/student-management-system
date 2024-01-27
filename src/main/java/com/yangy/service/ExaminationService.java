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

    List<Examination> getAll();

    Date getDateByExamId(String id);

    String getDateNameByExamId(String id);

    String getIdByNameAndDate(String examName, java.util.Date examDate);

    String getIdByExamName(String examName);

    Examination getExamById(String examId);

    String getIdByExamNameAndGrade(String examValue, String gradeValue);

    String getIdByExamNameAndGradeAndMajorId(String examValue, String gradeValue, String getmajorByName);

    List<String> getSubjectListByExamNameAndGradeAndMajor(String examValue, String gradeValue, String majorValue);
}
