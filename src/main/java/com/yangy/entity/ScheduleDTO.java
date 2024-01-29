package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTO {
    private String scheduleId;
    private ArrayList<Integer> weekly;//周次
    private Integer week;//星期几
    private Integer section;//第几节课
    private String course; //课程id
    private String classroom;//教室
    private String teacher;//老师
    private Integer type;//考试，考察
}
