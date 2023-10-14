package com.yangy.service;

import com.yangy.entity.Admin;

import java.util.List;


public interface AdminService {
    //增加管理员信息
    void addAdmin(Admin admin);

    //通过id删除管理员
    void deleteAdminById(String id);

    //通过id修改管理员信息
    void updateAdminById(Admin admin);

    //通过id查看管理员信息
    Admin getAdminById(String id);

    //查看所有管理员信息
    List<Admin> getAllAdmins();
}
