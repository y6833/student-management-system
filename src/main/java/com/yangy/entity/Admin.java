package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_admin")
public class Admin {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String name;
    private String password;
    private String gender;
    private String email;
    private String phone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private String address;
    public Admin(String id, String name, String password, String gender, String email, String phone, Date birthday,String address) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = new java.sql.Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        this.birthday = new java.sql.Date(birthday.getTime());
    }
}
