package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_class")
public class Tclass {
    @TableId(value = "class_id", type = IdType.AUTO)
    private String classId; // 班级ID
    private String className; // 班级名称
    private String gradeId; // 年级编号
    private String majorId; // 专业编号
    private String headTeacherId; // 班主任ID

}
