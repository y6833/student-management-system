package com.yangy.pojo;

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
    private int score;
    private Date examDate;

    public Score(Integer id, String studentId, String courseId, int score, Date examDate) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
        this.examDate = new java.sql.Date(examDate.getTime());
    }

    public void setExamDate(Date examDate) {
        this.examDate = new java.sql.Date(examDate.getTime());
    }
}
