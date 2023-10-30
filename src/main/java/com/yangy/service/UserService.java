package com.yangy.service;

import com.yangy.entity.User;

import java.util.List;

public interface UserService {
    //增加用户信息
    Boolean addUser(User user);

    //通过id删除用户
    void deleteUserById(String id);

    //通过id修改用户信息
    Boolean updateUserById(User user);

    //通过id查看用户信息
    User getUserById(Integer id);

    //查看所有用户信息
    List<User> getAllUsers();

    User selectByUsername(String username);

    List<User> selectPage(Integer pageNum, Integer pageSize);

    Integer selectTotal();

    boolean removeByUsername(String id);

    User selectByRoleId(String roleId);

    boolean updateIsActivate(String roleId, Integer isActivate);

    boolean login(User user);

}
