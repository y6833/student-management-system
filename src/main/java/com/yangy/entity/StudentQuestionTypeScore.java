package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_studentquestiontypescore")
public class StudentQuestionTypeScore {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String studentId;
    private String courseId;
    private int questionTypeId;
    private Float score;
    private Date examTime;

    public StudentQuestionTypeScore( String studentId, String courseId, int questionTypeId, Float score, Date examTime) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.questionTypeId = questionTypeId;
        this.score = score;
        this.examTime =  new java.sql.Date(examTime.getTime());
    }

    public void setExam_time(Date examTime) {
        this.examTime =  new java.sql.Date(examTime.getTime());
    }
}
