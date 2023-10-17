package com.yangy.controller.admin;

import com.yangy.entity.User;
import com.yangy.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sms/admin/user")
@Component("adminUserController")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Map<String,Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize){
        pageNum = (pageNum - 1) * pageSize;
        List<User> data = userService.selectPage(pageNum, pageSize);
        Integer total = userService.selectTotal();
        Map<String,Object> res = new HashMap<>();
        res.put("data",data);
        res.put("total", total);
        log.info("查询user数据：{}", res);
        return res;
    }

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PostMapping("/updataUser")
    public void updataUser(@RequestBody User user){
        if(userService.selectByUsername(user.getUsername()) == null){
            userService.addUser(user);
        }else{
            user.setId(userService.selectByUsername(user.getUsername()).getId());
            userService.updateUserById(user);

        }
    }

    @GetMapping("/getUser")
    public User getUserById(@RequestParam String id){
        return userService.selectByUsername(id);
    }

}
