package com.yangy.controller.admin;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangy.common.Result;
import com.yangy.entity.Teacher;
import com.yangy.service.ClassService;
import com.yangy.service.MajorService;
import com.yangy.service.TeacherService;
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

@RestController
@RequestMapping("/sms/admin/teacher")
@Slf4j
@Api(tags = "教师信息相关接口")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClassService classService;
    @Autowired
    private MajorService majorService;
    /**
     * 导入接口
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        reader.addHeaderAlias("教职工号","id");
        reader.addHeaderAlias("密码","password");
        reader.addHeaderAlias("姓名","name");
        reader.addHeaderAlias("性别","gender");
        reader.addHeaderAlias("出生日期","birthday");
        reader.addHeaderAlias("年级","grade");
        reader.addHeaderAlias("班级","classId");
        reader.addHeaderAlias("邮箱","email");
        reader.addHeaderAlias("手机号","phone");
        reader.addHeaderAlias("地址","address");
        List<Teacher> list = reader.readAll(Teacher.class);
//        System.out.println(list);
        teacherService.saveTeacherList(list);
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
        List<Teacher> list = teacherService.findAllOver();

        //通过工具类创建writer写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");

        //在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id","教职工号");
        writer.addHeaderAlias("password","密码");
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("gender","性别");
        writer.addHeaderAlias("birthday","出生日期");
        writer.addHeaderAlias("grade","年级");
        writer.addHeaderAlias("classId","班级");
        writer.addHeaderAlias("email","邮箱");
        writer.addHeaderAlias("phone","手机号");
        writer.addHeaderAlias("address","地址");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("教师信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();


    }




    //mybatis-plus分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize
            , @RequestParam String searchString) {

        IPage<Teacher> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(!"".equals(searchString)){
            Map<String, String> stringStringMap = Pagetool.parseParams(searchString);
            Set<String> keys = stringStringMap.keySet();
            for (String key: keys) {
                String value = stringStringMap.get(key);
                if("class_id".equals(key)){
                    value = classService.getIdByclassName(value);
                }else if("major".equals(key)){
                    value = majorService.getIdByclassName(value);
                }else if("birthday".equals(key)){
                    value = DateHelp.dataToString(value);
                }
                queryWrapper.like(key, value);
            }

        }
        IPage<Teacher> teacherpage = teacherService.getPage(page,queryWrapper);

        return Result.success(teacherpage);
    }



    //新增
    @PostMapping
    public Result save(@RequestBody Teacher teacher){
        boolean b = teacherService.saveTeacher(teacher);
        if(b){
            return Result.success();
        }else{
            return Result.error();
        }
    }

    //修改
    @PostMapping("/updata")
    public Result updata(@RequestBody Teacher teacher){
        boolean b = teacherService.updataTeacher(teacher);
        if(b){
            return Result.success();
        }
        return Result.error();
    }

    //查找所有
    @GetMapping
    public List<Teacher> findALl(){
        return teacherService.findAll();
    }


    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id){
        boolean b = teacherService.removeByTeaId(id);
        if (b){
            return Result.success();
        }
        return Result.error();
    }

    @GetMapping("/getTeacherByRoleId/{roleId}")
    public Result getTeacherByRoleId(@PathVariable String roleId){
        Teacher teacher = teacherService.getTeacherByRoleId(roleId);
        if(teacher != null){
            return Result.success(teacher);
        }
        return Result.error();
    }
}
