package com.yangy.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/user/student")
@Slf4j
@Api(tags = "学生信息相关接口")
public class StudentController {
}
