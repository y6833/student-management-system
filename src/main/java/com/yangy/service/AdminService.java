package com.yangy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yangy.entity.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdminByRoleId(String roleId);

    IPage<Admin> getPage(IPage<Admin> page, QueryWrapper<Admin> queryWrapper);

    boolean saveAdmin(Admin admin);

    boolean updataAdmin(Admin admin);

    boolean removeByTeaId(String id);

    void saveAdminList(List<Admin> list);

    List<Admin> findAllOver();

    String getNameById(String uid);
}
