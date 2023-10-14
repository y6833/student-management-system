package com.yangy.service.impl;

import com.yangy.mapper.AdminMapper;
import com.yangy.entity.Admin;
import com.yangy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void addAdmin(Admin admin) {
        adminMapper.addAdmin(admin);
    }

    @Override
    public void deleteAdminById(String id) {
        adminMapper.deleteAdminById(id);
    }

    @Override
    public void updateAdminById(Admin admin) {
        adminMapper.updateAdminById(admin);
    }

    @Override
    public Admin getAdminById(String id) {
        return adminMapper.getAdminById(id);
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminMapper.getAllAdmins();
    }
}
