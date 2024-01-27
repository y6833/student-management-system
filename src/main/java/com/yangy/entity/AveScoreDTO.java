package com.yangy.entity;

import lombok.Data;

@Data
public class AveScoreDTO {
 private String grade;//年级
    private String classs;//班级
    private Double averageGrade;//年级平均分
    private Double averageClass;//班级平均分
    private String examName;//考试名称
}
