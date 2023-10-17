package com.yangy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangy.entity.Score;
import com.yangy.mapper.ScoreMapper;
import com.yangy.service.ScoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> findByStudentId(String studentId) {
        return scoreMapper.findByStudentId(studentId);
    }

}
