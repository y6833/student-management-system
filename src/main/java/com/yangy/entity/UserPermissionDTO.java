package com.yangy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPermissionDTO {
    private String userId;  //用户id
    private String name;//姓名
    private String identity; //身份
    private List<Integer> permission;//用户权限
}
