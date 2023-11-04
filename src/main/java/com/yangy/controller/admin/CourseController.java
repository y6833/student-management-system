package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.service.CourseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/course")
@Component("adminCourseController")
@Slf4j
@Api(tags = "B端-课表相关接口")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/getSubjectList")
    public Result getSubjectList(){
        List<String> subjectList = courseService.getSubjectList();
        return Result.success(subjectList);
    }

}
