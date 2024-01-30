package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRankDTO {
    private String course;//科目名称
    private Double subject;//科目分数
    private Double Average;//平均分
    private Integer Max;//科目最大值
    private Integer classRangk;//班级排名
    private Integer GradeRangk;//年级排名
}
