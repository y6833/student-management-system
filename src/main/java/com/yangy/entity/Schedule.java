package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("tb_schedule")
public class Schedule {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String scheduleId;
    private Integer weekly;//周次
    private Integer week;//星期几
    private Integer section;//第几节课
    private String course; //课程id
    private String classroom;//教室

    public Schedule(String scheduleId, Integer weekly, Integer week, Integer section, String course, String classroom) {
        this.scheduleId = scheduleId;
        this.weekly = weekly;
        this.week = week;
        this.section = section;
        this.course = course;
        this.classroom = classroom;
    }

    public Schedule() {
    }
}
