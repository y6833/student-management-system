package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangy.util.MD5;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_students")
public class Student {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String name;
//    @JsonIgnore //在前端不展示密码
    private String password;
    private String gender;
    private Date birthday;
    private String grade;
    private String classId;
    private String email;
    private String phone;
    private String address;
    private String major;


    public Student(String id, String name, String password, String gender, Date birthday, String grade, String classId, String email, String phone, String address, String major) {
        this.id = id;
        this.name = name;
        this.password = MD5.encrypt(password);
        this.gender = gender;
        this.birthday = new java.sql.Date(birthday.getTime());
        this.grade = grade;
        this.classId = classId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.major = major;
    }

    public void setBirthday(Date birthday) {
        this.birthday = new java.sql.Date(birthday.getTime());
    }

    public void setPassword(String password) {
        this.password = MD5.encrypt(password);
    }
}
