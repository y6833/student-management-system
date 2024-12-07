package com.yangy.controller.admin;

import com.yangy.common.Result;
import com.yangy.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms/admin/dashboard")
@Slf4j
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/statistics")
    public Result getStatistics() {
        return Result.success(dashboardService.getStatistics());
    }

    @GetMapping("/gender-ratio")
    public Result getGenderRatio() {
        return Result.success(dashboardService.getGenderRatio());
    }

    @GetMapping("/major-distribution")
    public Result getMajorDistribution() {
        return Result.success(dashboardService.getMajorDistribution());
    }

    @GetMapping("/student-trend")
    public Result getStudentTrend() {
        return Result.success(dashboardService.getStudentTrend());
    }
}
