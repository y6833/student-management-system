package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeNumDTO {
    private Integer gradePeople;//年级人数
    private Double maxScore;//最高分
    private Double minScore;//最低分
    private Double aveScore;//平均分
}
