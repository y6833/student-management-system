package com.yangy.service;

import com.yangy.entity.UserPermissionDTO;

import java.util.List;

public interface UserPermissionService {

    List<UserPermissionDTO> getAll();

    boolean addUserPermission(String userId, int permissionId);

    boolean deletePermission(String userId, int permissionId);

    List<Integer> getUserPermission(String roleId);

    List<Integer> getUserPermissionList(String roleId);
}
