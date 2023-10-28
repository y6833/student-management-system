package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.service.MajorService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sms/admin/major")
@Slf4j
@Api(tags = "专业相关接口")
public class MajorController {

    @Autowired
    private MajorService majorService;

    /**
     * 获取专业列表
     * @return
     */
    @GetMapping("/getMajorList")
    public Result getMajorList(){
        List<String> majorList = majorService.getMajorList();
        return Result.success(majorList);
    }
}
