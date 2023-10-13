package com.yangy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_admin")
public class Admin {
    @TableId(value = "id",type = IdType.AUTO)
    private String id;
    private String username;
    private String password;
    private String gender;
    private String email;
    private String phone;
    private Date birthday;

    public Admin(String id, String username, String password, String gender, String email, String phone, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.birthday = new java.sql.Date(birthday.getTime());
    }

    public void setBirthday(Date birthday) {
        this.birthday = new java.sql.Date(birthday.getTime());
    }
}
