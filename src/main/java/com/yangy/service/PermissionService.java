package com.yangy.service;

import com.yangy.pojo.Permission;

import java.util.List;

public interface PermissionService {
    void addPermission(Permission permission); // 添加权限

    void deletePermissionById(Integer id); // 根据ID删除权限

    void updatePermissionById(Permission permission); // 根据ID更新权限

    Permission getPermissionById(Integer id); // 根据ID获取权限信息

    List<Permission> getAllPermissions(); // 获取所有权限信息
}
