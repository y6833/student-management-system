package com.yangy.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangy.common.Constants;
import com.yangy.common.Result;
import com.yangy.entity.Files;
import com.yangy.service.FilesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sms/file")
@Slf4j
@Api(tags = "文件相关接口")
public class FilesController {
    @Autowired
    private FilesService fileService;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        //定义一个文件唯一的标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUuid = uuid + StrUtil.DOT + type;
        File uploadFile = new File(fileUploadPath + fileUuid);
        //判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        String url;
        //上传文件到磁盘
        file.transferTo(uploadFile);

        //获取文件的md5
        String md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否存在相同的记录
        Files dbFiles = getFileMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
            //由于文件已经存在，所以删除刚才上传的重复文件
                uploadFile.delete();
//            return url;
        } else {
            //数据库不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:9001/sms/file/" + fileUuid;
        }
        //存储数据库
        Files saveFile = new Files();
        if(dbFiles != null && dbFiles.getIsDelete()){
            saveFile.setIsDelete(false);
        }
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);  //从b转化为kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.savefile(saveFile);
        return Result.success(url);


    }

    //文件下载接口  就是前面上传后返回的url：http://localhost:8081/file/{fileUuid}
    @GetMapping("/{fileUuid}")
    public Result download(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        //判断文件是否禁用或者被删除
        String fileURL = "http://localhost:9001/sms/file/" + fileUuid;
        Files fileOne = fileService.findByURL(fileURL);
        if (fileOne.getIsDelete() == true || fileOne.getEnable() == false) {
            return Result.error(Constants.CODE_307, "文件不存在");
        }

        //根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUuid);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();

        return Result.success("下载成功");

    }

    //分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name,
                            @RequestParam(defaultValue = "") String type) {
        return Result.success(fileService.findPage(pageNum, pageSize, name, type));

    }

    //删除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileService.findById(id);
        files.setIsDelete(true);
        fileService.updateById_(files);
        return Result.success();
    }

    //批量删除
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileService.getlist(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileService.updateById_(file);

        }
        return Result.success();
    }

    //更新一个字段
    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        return Result.success(fileService.updateById_(files));
    }

    //获取文件类型列表
    @GetMapping("/getfiletypelist")
    public Result getFileTypeList(){
        List<String> fileTypeList = fileService.findFileTypeList();
        Set<String> uniqueFileTypeSet = new HashSet<>(fileTypeList);
        List<String> uniqueFileTypeList = new ArrayList<>(uniqueFileTypeSet);
        return Result.success(uniqueFileTypeList);
    }


    //通过文件的md5查询文件
    private Files getFileMd5(String md5) {
        //查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileService.getList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);

    }

}
