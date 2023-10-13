package com.yangy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangy.util.MD5;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_teachers")
public class Teacher {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String name;
    private String password;
    private String gender;
    private Date birthday;
    private String grade;
    private String classId;
    private String email;
    private String phone;
    private String address;

    public Teacher(String id, String name, String password, String gender, Date birthday, String grade, String classId, String email, String phone, String address) {
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
    }

    public void setBirthday(Date birthday) {
        this.birthday = new java.sql.Date(birthday.getTime());
    }
    public void setPassword(String password) {
        this.password = MD5.encrypt(password);
    }
}
