package com.yangy.controller.admin;

import com.yangy.common.Constants;
import com.yangy.common.Result;
import com.yangy.entity.Admin;
import com.yangy.entity.Student;
import com.yangy.entity.Teacher;
import com.yangy.entity.User;
import com.yangy.service.AdminService;
import com.yangy.service.StudentService;
import com.yangy.service.TeacherService;
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

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

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

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user){

        Boolean aBoolean = userService.addUser(user);
        if (aBoolean){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        boolean b = userService.removeByUsername(id);
        if(b){
            return Result.success();
        }
        return Result.error();
    }

    @PostMapping("/updataUser")
    public Result updataUser(@RequestBody User user){
        if(userService.selectByUsername(user.getUsername()) == null){
            return Result.error();
//            userService.addUser(user);
        }else{
            user.setId(userService.selectByUsername(user.getUsername()).getId());
            Boolean aBoolean = userService.updateUserById(user);
            if (aBoolean){
                return Result.success();
            }
        }
        return Result.error();
    }

    //通过学号来修改用户激活
    @GetMapping("/updateIsActivate")
    public Result updateIsActivate(@RequestParam String roleId, @RequestParam Integer isActivate) {
        if (userService.selectByRoleId(roleId) == null) {
            return Result.error();
        }
        return Result.success(userService.updateIsActivate(roleId, isActivate));
    }

    @GetMapping("/getUser")
    public Result getUserById(@RequestParam String id){
        User user = userService.selectByUsername(id);
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        Result result = new Result();
        User u = userService.login(user);
        if(u != null) {
            result.setCode(Constants.CODE_200);
            switch (u.getRoleName()){
                case 1: break;
                case 2:
                    Admin admin = adminService.getAdminByRoleId(u.getRoleId());
                    result.setData(admin);
                    break;
                case 3:
                    Teacher teacher = teacherService.getTeacherByRoleId(u.getRoleId());
                    result.setData(teacher);
                    break;
                case 4:
                    Student student = studentService.getStudentRoleId(u.getRoleId());
                    result.setData(student);
                    break;
            }
        } else {
            result.setCode(Constants.CODE_500);
        }
        return result;
    }

}
