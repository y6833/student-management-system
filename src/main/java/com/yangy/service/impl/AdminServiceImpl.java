package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Admin;
import com.yangy.entity.User;
import com.yangy.mapper.AdminMapper;
import com.yangy.service.AdminService;
import com.yangy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    @Autowired
    private UserService userService;

    @Override
    public Admin getAdminByRoleId(String roleId) {
        return adminMapper.selectByRoleId(roleId);
    }

    @Override
    public IPage<Admin> getPage(IPage<Admin> page, QueryWrapper<Admin> queryWrapper) {
        List<Admin> adminList = page(page, queryWrapper).getRecords();
        IPage<Admin> adminIPage = page(page, queryWrapper).setRecords(adminList);
        return adminIPage;
    }

    @Override
    public boolean saveAdmin(Admin admin) {
        return save(admin);
    }

    @Override
    public boolean updataAdmin(Admin admin) {
        return updateById(admin);
    }

    @Override
    public boolean removeByTeaId(String id) {
        return adminMapper.removeByTeaId(id);
    }

    @Override
    public void saveAdminList(List<Admin> list) {
        for (Admin admin : list) {
            User user = new User(admin.getId(),admin.getPassword(), 1,admin.getId(),2,"");
            userService.addUser(user);
        }
        saveBatch(list);
    }

    @Override
    public List<Admin> findAllOver() {
        List<Admin> admins = list();
        return admins;
    }

    @Override
    public String getNameById(String uid) {
        return adminMapper.getNameById(uid);
    }
}
