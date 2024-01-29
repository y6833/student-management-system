package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassRankDTO {
    private List<String> examList;//考试列表
    private List<Integer> classNum;//班级人数列表
    private List<Double> classProportion;//班级占比
}
