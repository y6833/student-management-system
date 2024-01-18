package com.yangy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Major;
import com.yangy.mapper.MajorMapper;
import com.yangy.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {

    @Resource
    private MajorMapper majorMapper;
    @Override
    public String getMajorName(String major) {
        return majorMapper.getMajorName(major);
    }

    @Override
    public List<String> getMajorList() {
        return majorMapper.getMajorList();
    }

    @Override
    public String getIdByclassName(String value) {
        return majorMapper.getIdByclassName(value);
    }

    @Override
    public IPage<Major> getPage(IPage<Major> page, QueryWrapper<Major> queryWrapper) {
        List<Major> majorList = page(page, queryWrapper).getRecords();
        IPage<Major> majorIPage = page(page, queryWrapper).setRecords(majorList);
        return majorIPage;
    }

    @Override
    public boolean updatamajor(Major major) {
        return updateById(major);
    }

    @Override
    public boolean removeById(String id) {
        return majorMapper.removeById(id);
    }

    @Override
    public boolean saveMajor(Major major) {
        return save(major);
    }

}
