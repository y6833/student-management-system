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
@TableName("tb_schedules")
public class Schedules {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String name;
    private String classs;//班级
    private Integer grade;//年级编号
    private Integer stage;//学期（1上/2下）
}
