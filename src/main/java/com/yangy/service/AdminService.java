package com.yangy.service;

import com.yangy.entity.Admin;

public interface AdminService {

    Admin getAdminByRoleId(String roleId);
}
