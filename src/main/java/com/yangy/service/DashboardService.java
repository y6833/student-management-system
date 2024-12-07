package com.yangy.service;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    /**
     * 获取统计数据
     */
    Map<String, Object> getStatistics();

    /**
     * 获取性别比例
     */
    Map<String, Integer> getGenderRatio();

    /**
     * 获取专业分布
     */
    List<Map<String, Object>> getMajorDistribution();

    /**
     * 获取学生增长趋势
     */
    List<Map<String, Object>> getStudentTrend();
}
