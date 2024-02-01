package com.yangy.entity;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class StudentScores {
    private Student student;//学生
    private Map<String, Double> scores; //成绩
    private String scoreId;//成绩id
    private Integer classRanking; //班级排名
    private Integer gradeRanking;//年级排名
    private Date examDate; // 考试日期
    private String examName;//考试名称
    private String proposal;//建议
    private Integer active;//是否参与考试1参与/0未参与
    private String examRoom;//考试教室
    private Integer type;//考试1，测试0
}
