package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.entity.User;
import com.yangy.entity.UserPermissionDTO;
import com.yangy.entity.UserPermissionDTOs;
import com.yangy.service.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sms/admin/userpermission")
@Slf4j
@Api(tags = "用户权限关相关接口")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param searchString
     * @return
     */
    @GetMapping("/getPermissionList")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString){
        //获取所有权限
//        userPermissionService.getAll(); //获取所有的权限数据
        List<UserPermissionDTO> userPermissionsList = new ArrayList<>();
        List<User> userList = userService.getAllUsers();
        for (User user : userList) {
            UserPermissionDTO userPermissionDTO = new UserPermissionDTO();
            userPermissionDTO.setUserId(user.getRoleId());
            switch (user.getRoleName()){
                case 1:
                    userPermissionDTO.setIdentity("超级管理员");
                    userPermissionDTO.setName(adminService.getNameById(user.getRoleId()));
                    break;//身份
                case 2:userPermissionDTO.setIdentity("管理员");userPermissionDTO.setName(adminService.getNameById(user.getRoleId()));break;
                case 3:userPermissionDTO.setIdentity("老师");
                    userPermissionDTO.setName(teacherService.getNameById(user.getRoleId()));
                    break;
                case 4:userPermissionDTO.setIdentity("学生");
                    userPermissionDTO.setName(studentService.getNameById(user.getRoleId()));
                    break;
            }
            userPermissionDTO.setPermission(userPermissionService.getUserPermissionList(user.getRoleId()));
            userPermissionsList.add(userPermissionDTO);
        }
        List<UserPermissionDTO> userPermissionList = new ArrayList<>();//用于接收通过条件过滤的数据

        if(searchString.equals("")){
            userPermissionList = userPermissionsList;
        }else{
            for (UserPermissionDTO userPermissionDTO : userPermissionsList) {
                if (userPermissionService.findLikeList(searchString).contains(userPermissionDTO.getUserId())){
                    userPermissionList.add(userPermissionDTO);
                }
            }
        }

        List<UserPermissionDTO> userPermissionLists = new ArrayList<>(); //分页后的数据
        //进行分页
        for (int i = (pageNum -1)*pageSize; i < pageSize*pageNum; i++) {
            if(userPermissionList.size()> i && userPermissionList.get(i) !=null){
                userPermissionLists.add(userPermissionList.get(i));
            }
        }
        UserPermissionDTOs userPermissionDTOs = new UserPermissionDTOs(userPermissionLists,userPermissionList.size());
        return Result.success(userPermissionDTOs);

    }

    /**
     * 添加权限
     * @param userId
     * @param permissionId
     * @return
     */
    @GetMapping("/addUserPermission")
    public Result addUserPermission(@RequestParam String userId, @RequestParam int permissionId){
        boolean b = userPermissionService.addUserPermission(userId, permissionId);
        if(b){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 删除权限
     * @param userId
     * @param permissionId
     * @return
     */
    @GetMapping("/deletePermission")
    public Result deletePermission(@RequestParam String userId, @RequestParam int permissionId) {
        boolean b = userPermissionService.deletePermission(userId, permissionId);
        if (b) {
            return Result.success();
        }
        return Result.error();
    }

    @GetMapping("/getUserPermission")
    public Result getUserPermission(@RequestParam String roleId){
        List<Integer> userPermission = userPermissionService.getUserPermission(roleId);
        return Result.success(userPermission);
    }

}
