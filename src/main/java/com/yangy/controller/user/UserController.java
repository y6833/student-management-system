package com.yangy.controller.user;

import com.yangy.common.Constants;
import com.yangy.common.Result;
import com.yangy.entity.Student;
import com.yangy.entity.User;
import com.yangy.service.StudentService;
import com.yangy.service.UserService;
import com.yangy.util.TokenUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sms/user/user")
@Component("userUserController")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;
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

    @GetMapping("/getUser")
    public Result getUserById(@RequestParam String id) {
        User user = userService.selectByRoleId(id);
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        boolean login = userService.login(user);
        if (login) {
            User u = userService.selectByUsername(user.getUsername());
            if (u.getActivation() == 1 && u.getRoleName() == 4) {
                Student student = studentService.getStudentRoleId(u.getRoleId());
                u.setName(student.getName());
                String token = TokenUtils.genToken(u.getUsername(), u.getPassword());
                u.setToken(token);
                String avatarURL = userService.getAvatarURL(user.getUsername());
                u.setAvatar(avatarURL);
                return Result.success(u);
            } else {
                return Result.error(Constants.CODE_304, "用户未激活");
            }
        } else {
            return Result.error(Constants.CODE_305, "账户或密码错误");
        }
    }

}
