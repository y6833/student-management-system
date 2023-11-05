package com.yangy.service;

import com.yangy.entity.Examination;
import com.yangy.entity.Score;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ScoreService {



    List<Score> findByStudentId(String studentId);

    List<Map<String, Double>> findScoreByStuId(String id);

    List<Examination> getExamTimes();

//获取成绩键值对
    Map<String, Double> findScoreByStuIdAndExamDate(String id, Date examDate);
//获取班级排名
    Integer getClassRanking(String id, String classId, Date examDate);
//获取年级排名，需要知道学生id，学生所在年级d,专业,已考试的时间 同年级不同专业无法排名
    Integer getGradeRanking(String id, String grade, String major, Date examDate);

    boolean updataScore(String id, String courseName, Double scores,Date examDate);

    List<String> getExamList();

    boolean addStudentScore(Score score1);

    boolean deleteStuScore(String studentId, Date examData);

    List<Score> findScoreStuByStuIdAndExamDate(String studentId, Date examData);

    boolean deleteById(Integer id);

}
