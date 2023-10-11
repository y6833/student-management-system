package com.yangy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_courses")
public class Courses {
    @TableId(value = "course_id",type = IdType.AUTO)
    private Integer courseId;
    private String name;
    private String teacher;
    private Integer teacherId;
    private String remark;
}
