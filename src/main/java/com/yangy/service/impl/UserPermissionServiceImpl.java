package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.UserPermission;
import com.yangy.entity.UserPermissionDTO;
import com.yangy.mapper.UserPermissionMapper;
import com.yangy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper, UserPermission> implements UserPermissionService {
    @Resource
    private UserPermissionMapper userPermissionMapper; // mapper对象注入
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;
    @Override
    public List<UserPermissionDTO> getAll() {
        ArrayList<UserPermissionDTO> userPermissionDTOS = new ArrayList<>();
        List<String> userIdList = userPermissionMapper.getAllUserId();//获取权限表中所有用户的id
        for (String uid : userIdList) {
            UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
            userPermissionDTO.setUserId(uid);
            //通过uid再user表中查看是什么身份
            int roleName = userService.getRoleNamebyRoleId(uid);
            switch (roleName){
                case 1:
                    userPermissionDTO.setIdentity("超级管理员");
                    userPermissionDTO.setName(adminService.getNameById(uid));
                    break;//身份
                case 2:userPermissionDTO.setIdentity("管理员");userPermissionDTO.setName(adminService.getNameById(uid));break;
                case 3:userPermissionDTO.setIdentity("老师");
                    userPermissionDTO.setName(teacherService.getNameById(uid));
                    break;
                case 4:userPermissionDTO.setIdentity("学生");
                    userPermissionDTO.setName(studentService.getNameById(uid));
                    break;
            }
            userPermissionDTO.setPermission(userPermissionMapper.getUserPermissionList(uid));
            userPermissionDTOS.add(userPermissionDTO);
        }
        return userPermissionDTOS;
    }

    @Override
    public boolean addUserPermission(String userId, int permissionId) {
        return userPermissionMapper.addUserPermission(userId, permissionId);
    }

    @Override
    public boolean deletePermission(String userId, int permissionId) {
        return userPermissionMapper.deletePermission(userId, permissionId);
    }

    @Override
    public List<Integer> getUserPermission(String roleId) {
        return userPermissionMapper.getUserPermissionList(roleId);
    }

    @Override
    public List<Integer> getUserPermissionList(String roleId) {
        return userPermissionMapper.getUserPermissionList(roleId);
    }

    @Override
    public List<String> findLikeList(String roleId) {
        List<String> likeList = userPermissionMapper.findLikeList(roleId);
        return likeList;
    }

}
