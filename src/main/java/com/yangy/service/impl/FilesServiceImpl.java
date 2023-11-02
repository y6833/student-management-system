package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Files;
import com.yangy.mapper.FilesMapper;
import com.yangy.service.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements FilesService {

    @Autowired
    FilesMapper filesMapper;

    @Override
    public IPage<Files> findPage(Integer pageNum,
                                 Integer pageSize,
                                 String  name,
                                 String type) {
        QueryWrapper<Files> queryWrapper=new QueryWrapper<>();
        //查询未删除的记录
        queryWrapper.eq("is_delete",false);
        queryWrapper.orderByDesc("id");
        if (! "".equals(name)){
            queryWrapper.like("name",name);
        }
        if(! "".equals(type)){
            queryWrapper.like("type",type);
        }
        return this.page(new Page<>(pageNum,pageSize),queryWrapper);
    }

    @Override
    public Files findById(Integer id) {
        return getById(id);
    }

    @Override
    public boolean updateById_(Files files) {
        return updateById(files);
    }

    @Override
    public List<Files> getlist(QueryWrapper<Files> queryWrapper) {
        return list(queryWrapper);
    }

    @Override
    public List<String> findFileTypeList() {
        return filesMapper.findFileTypeList();
    }


    @Override
    public void savefile(Files File) {
        if(filesMapper.getFileByMD5(File.getMd5()) != null){
            update(File, new QueryWrapper<Files>().eq("md5", File.getMd5()));
        }else{
            save(File);
        }
    }

    @Override
    public List<Files> getList(QueryWrapper<Files> queryWrapper) {
        return list(queryWrapper);
    }

    @Override
    public Files findByURL(String fileURL) {
        return filesMapper.findByURL(fileURL);
    }

}
