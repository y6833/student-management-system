package com.yangy.service;

import com.yangy.pojo.Role;

import java.util.List;

public interface RoleService {
    void addRole(Role role); // 添加角色

    void deleteRoleById(Integer id); // 根据ID删除角色

    void updateRoleById(Role role); // 根据ID更新角色

    Role getRoleById(Integer id); // 根据ID获取角色信息

    List<Role> getAllRoles(); // 获取所有角色信息
}
