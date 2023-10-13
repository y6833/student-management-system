package com.yangy.service.impl;

import com.yangy.mapper.UserMapper;
import com.yangy.pojo.User;
import com.yangy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
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
    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }
}
