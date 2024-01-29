package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamRangkDTO {
    private String examName;//考试名称
    private Integer studentNum;//这个班的考试人数

}
