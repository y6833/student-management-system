package com.yangy.service.impl;

import com.yangy.mapper.UserPermissionMapper;
import com.yangy.pojo.UserPermission;
import com.yangy.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {
    @Autowired
    private UserPermissionMapper userPermissionMapper; // mapper对象注入

    @Override
    public void addUserPermission(UserPermission userPermission) { // 添加用户权限
        userPermissionMapper.insert(userPermission);
    }

    @Override
    public void deleteUserPermissionById(Integer id) { // 根据ID删除用户权限
        userPermissionMapper.deleteById(id);
    }

    @Override
    public void updateUserPermissionById(UserPermission userPermission) { // 根据ID更新用户权限
        userPermissionMapper.updateById(userPermission);
    }

    @Override
    public UserPermission getUserPermissionById(Integer id) { // 根据ID获取用户权限信息
        return userPermissionMapper.selectById(id);
    }

    @Override
    public List<UserPermission> getAllUserPermissions() { // 获取所有用户权限信息
        return userPermissionMapper.selectAll();
    }
}
