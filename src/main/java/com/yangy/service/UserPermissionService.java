package com.yangy.service;

import com.yangy.entity.UserPermission;

import java.util.List;

public interface UserPermissionService {
    void addUserPermission(UserPermission userPermission); // 添加用户权限

    void deleteUserPermissionById(Integer id); // 根据ID删除用户权限

    void updateUserPermissionById(UserPermission userPermission); // 根据ID更新用户权限

    UserPermission getUserPermissionById(Integer id); // 根据ID获取用户权限信息

    List<UserPermission> getAllUserPermissions(); // 获取所有用户权限信息
}
