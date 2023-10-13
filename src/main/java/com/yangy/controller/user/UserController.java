package com.yangy.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/user/user")
@Component("userUserController")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {
}
