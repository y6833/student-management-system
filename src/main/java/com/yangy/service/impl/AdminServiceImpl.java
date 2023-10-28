package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Admin;
import com.yangy.mapper.AdminMapper;
import com.yangy.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin getAdminByRoleId(String roleId) {
        return adminMapper.selectByRoleId(roleId);
    }
}
