package com.yangy.service.impl;

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

}
