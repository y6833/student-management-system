package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.UserPermission;
import com.yangy.mapper.UserPermissionMapper;
import com.yangy.service.UserPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {
    @Resource
    private UserPermissionMapper userPermissionMapper; // mapper对象注入

}
