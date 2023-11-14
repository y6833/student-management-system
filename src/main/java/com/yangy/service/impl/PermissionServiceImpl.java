package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Permission;
import com.yangy.mapper.PermissionMapper;
import com.yangy.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper; // mapper对象注入


    @Override
    public List<Integer> getAllPermission() {
        return permissionMapper.getAllPermission();
    }
}
