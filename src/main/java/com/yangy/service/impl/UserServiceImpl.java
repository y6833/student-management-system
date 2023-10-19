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
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void deleteUserById(String id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUserById(User user) {
        userMapper.updateById(user);
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

}
