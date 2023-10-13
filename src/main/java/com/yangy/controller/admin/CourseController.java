package com.yangy.controller.admin;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/admin/course")
@Component("adminCourseController")
@Slf4j
@Api(tags = "B端-课表相关接口")
public class CourseController {
}
