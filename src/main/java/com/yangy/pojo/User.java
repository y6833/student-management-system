package com.yangy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private String password;
    private Integer activation; // 用户是否激活 0-未激活 1-激活
    private String verifiCode;// 登录验证码
    private String  roleId;
    private Integer roleName;

    public User(String username, String password,String verifiCode, Integer activation){
        this.username = username;
        this.password = MD5.encrypt(password);
        this.activation = activation;
        this.verifiCode = verifiCode;
    }

    public User(String username, String password, Integer activation, String roleId, Integer roleName) {
        this.username = username;
        this.password = MD5.encrypt(password);
        this.activation = activation;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public void setPassword(String password) {
        this.password = MD5.encrypt(password);
    }
}
