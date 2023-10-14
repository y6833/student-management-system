package com.yangy.service.impl;

import com.yangy.mapper.PermissionMapper;
import com.yangy.entity.Permission;
import com.yangy.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper; // mapper对象注入

    @Override
    public void addPermission(Permission permission) { // 添加权限
        permissionMapper.insert(permission);
    }

    @Override
    public void deletePermissionById(Integer id) { // 根据ID删除权限
        permissionMapper.deleteById(id);
    }

    @Override
    public void updatePermissionById(Permission permission) { // 根据ID更新权限
        permissionMapper.updateById(permission);
    }

    @Override
    public Permission getPermissionById(Integer id) { // 根据ID获取权限信息
        return permissionMapper.selectById(id);
    }

    @Override
    public List<Permission> getAllPermissions() { // 获取所有权限信息
        return permissionMapper.selectAll();
    }
}
