package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//考试
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ts_examination")
public class Examination {
    private String id;
    private String examName;
    private String examGrade;
    private String examMajor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date examDate;
    private String scheduleName;
}
