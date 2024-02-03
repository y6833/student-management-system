package com.yangy.controller.user;

import com.yangy.common.Constants;
import com.yangy.common.Result;
import com.yangy.entity.Student;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms/user/student")
@Component("userStudentController")
@Slf4j
@Api(tags = "学生信息相关接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private MajorService majorService;



    @GetMapping("/getStuByRoleId/{id}")
    public Result getStuByRoleId(@PathVariable String id){
        Student student = studentService.getStudentById(id);
        if (student != null) {
            student.setClassId(classService.getClassName(student.getClassId()));
            student.setMajor(majorService.getMajorName(student.getMajor()));
            return Result.success(student);

        }
        return Result.error(Constants.CODE_302,"学生不存在");
    }

    @PostMapping("/updata")
    public Result updata(@RequestBody Student student){
        boolean b = studentService.updataStudent(student);
        if(b){
            return Result.success();
        }
        return Result.error();
    }
}
