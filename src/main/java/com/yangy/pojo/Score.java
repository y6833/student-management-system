package com.yangy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_score")
public class Score {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String studentId;
    private String courseId;
    private int score;
    private Date examDate;
}
