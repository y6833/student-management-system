package com.yangy.controller;

import com.yangy.entity.User;
import com.yangy.service.UserService;
import com.yangy.util.CreateVerifiCodeImage;
import com.yangy.util.MD5;
import com.yangy.util.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/sms/system")
@Slf4j
@Api(tags = "公共功能相关接口")
public class SystemController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user,HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionverifiCode = (String) session.getAttribute("verifiCode");
        String loginverifiCode = user.getVerifiCode();
        if("".equals(sessionverifiCode) || null == sessionverifiCode){
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        if(!sessionverifiCode.equalsIgnoreCase(loginverifiCode)){
            return Result.fail().message("验证码输入错误,请刷新后重新输入");
        }
        // 在session域中移除验证码
        session.removeAttribute("verifiCode");

        User user1 = userService.selectByUsername(user.getUsername());
        if(user1 != null && MD5.encrypt(user.getPassword()).equals(user1.getPassword())){
            return Result.fail().message("用户名或密码错误！");
        }


        return Result.ok();
    }

    /**
     * 登录时候的验证码
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        // 将验证码图片响应到浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
