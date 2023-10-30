package com.yangy.controller.admin;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Admin;
import com.yangy.service.AdminService;
import com.yangy.util.DateHelp;
import com.yangy.util.Pagetool;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 管理员相关接口
 */
@RestController
@RequestMapping("/sms/admin/admin")
@Slf4j
@Api(tags = "管理员相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;
    //mybatis-plus分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Admin> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)){
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key: keys) {
                String value = stringStringMap.get(key);
                if("birthday".equals(key)){
                    value = DateHelp.dataToString(value);
                }
                queryWrapper.like(key, value);
            }

        }
        IPage<Admin> adminpage = adminService.getPage(page,queryWrapper);

        return Result.success(adminpage);
    }


    //新增
    @PostMapping
    public Result save(@RequestBody Admin admin){
        boolean b = adminService.saveAdmin(admin);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    //修改
    @PostMapping("/updata")
    public Result updata(@RequestBody Admin admin){
        boolean b = adminService.updataAdmin(admin);
        if(b){
            return Result.success();
        }
        return Result.error();
    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        boolean b = adminService.removeByTeaId(id);
        if (b){
            return Result.success();
        }
        return Result.error();
    }

    /**
     * 导入接口
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("管理员ID","id");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("性别","gender");
        reader.addHeaderAlias("出生日期","birthday");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("手机号","phone");
        reader.addHeaderAlias("地址","address");

        List<Admin> list = reader.readAll(Admin.class);
//        System.out.println(list);
        adminService.saveAdminList(list);
        return true;
    }

    /**
     * 导出接口
     * @param response
     * @throws Exception
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
        //从数据库查询所有的数据
        List<Admin> list = adminService.findAllOver();

        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id","管理员ID");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("gender","性别");
        writer.addHeaderAlias("birthday","出生日期");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","手机号");
        writer.addHeaderAlias("address","地址");


        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("管理员信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();


    }

    /**
     * 根据id获取管理员信息
     * @param roleId
     * @return
     */
    @GetMapping("/getAdminByRoleId/{roleId}")
    public Result getAdminByRoleId(@PathVariable String roleId){
        Admin admin = adminService.getAdminByRoleId(roleId);
        if(admin != null){
            return Result.success(admin);
        }
        return Result.error();
    }



}
