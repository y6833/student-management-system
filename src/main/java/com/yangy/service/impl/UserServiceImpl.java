package com.yangy.service.impl;

import com.yangy.entity.User;
import com.yangy.mapper.UserMapper;
import com.yangy.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public void deleteUserById(String id) {
        userMapper.deleteById(id);
    }

    @Override
    public Boolean updateUserById(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public List<User> selectPage(Integer pageNum, Integer pageSize) {
        return userMapper.selectPage(pageNum, pageSize);
    }

    @Override
    public Integer selectTotal() {
        return userMapper.selectTotal();

    }

    @Override
    public boolean removeByUsername(String id) {
        return userMapper.removeByUsername(id);
    }

    @Override
    public User selectByRoleId(String roleId) {
        return userMapper.selectByRoleId(roleId);
    }

    @Override
    public boolean updateIsActivate(String roleId, Integer isActivate) {
        return userMapper.updateIsActivate(roleId,isActivate);
    }

    @Override
    public boolean login(User user) {
        String password = user.getPassword();
        User u = userMapper.selectByUsername(user.getUsername());
        if (u != null && u.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public String getAvatarURL(String username) {
        return userMapper.getAvatarURL(username);
    }

    @Override
    public int getRoleNamebyRoleId(String uid) {
        return userMapper.getRoleNamebyRoleId(uid);
    }

    @Override
    public String getRoleIdByUsername(String id) {
        return userMapper.getRoleIdByUsername(id);
    }

}
