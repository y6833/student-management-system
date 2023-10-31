package com.yangy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yangy.util.MD5;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String name;
//    @JsonIgnore
    private String password;
    private Integer activation; // 用户是否激活 0-未激活 1-激活
    private String verifiCode;// 登录验证码
    private String  roleId;
    private Integer roleName;
    private String token;
    @TableField(value = "avatar_url")  //绑定数据库中的字段
    private String avatar; //头像

    public User(String username, String password,String verifiCode, Integer activation){
        this.username = username;
        this.password = password.length()>=20 ? password : MD5.encrypt(password);
        this.activation = activation;
        this.verifiCode = verifiCode;
    }

    public User(String username, String password, Integer activation, String roleId, Integer roleName,String avatar) {
        this.username = username;
        this.password = password.length()>=20 ? password : MD5.encrypt(password);
        this.activation = activation;
        this.roleId = roleId;
        this.roleName = roleName;
        this.avatar = avatar;
    }


    public void setPassword(String password) {
        this.password = password.length()>=20 ? password : MD5.encrypt(password);
    }
}
