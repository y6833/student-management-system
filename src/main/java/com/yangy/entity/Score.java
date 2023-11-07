package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_score")
public class Score {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String studentId;
    private String courseId;
    private Double score;
    private Double scoreMax;
    private Double averageClassScore;//班级平均分
    private Double averageGradeScore;//年级平均分
    private Date examDate;

    public Score(String studentId, String courseId, Double score, Double scoresMax, Double averageClassScore, Double averageGradeScore, Date examDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.scoreMax = scoresMax;
        this.averageClassScore = averageClassScore;
        this.averageGradeScore = averageGradeScore;
        this.examDate = examDate;
    }

    public Score(Integer id, String studentId, String courseId, Double score, Date examDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.examDate = new java.sql.Date(examDate.getTime());
    }

    public Score(String studentId, String courseId, Double score, Date examDate) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.examDate = examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = new java.sql.Date(examDate.getTime());
    }
}
