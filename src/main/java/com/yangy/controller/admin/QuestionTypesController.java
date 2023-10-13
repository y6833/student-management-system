package com.yangy.controller.admin;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/admin/questiontypes")
@Slf4j
@Api(tags = "题型相关接口")
public class QuestionTypesController {
}