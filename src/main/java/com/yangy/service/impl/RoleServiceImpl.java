package com.yangy.service.impl;

import com.yangy.mapper.RoleMapper;
import com.yangy.entity.Role;
import com.yangy.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper; // mapper对象注入

    @Override
    public void addRole(Role role) { // 添加角色
        roleMapper.insert(role);
    }

    @Override
    public void deleteRoleById(Integer id) { // 根据ID删除角色
        roleMapper.deleteById(id);
    }

    @Override
    public void updateRoleById(Role role) { // 根据ID更新角色
        roleMapper.updateById(role);
    }

    @Override
    public Role getRoleById(Integer id) { // 根据ID获取角色信息
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> getAllRoles() { // 获取所有角色信息
        return roleMapper.selectAll();
    }
}
