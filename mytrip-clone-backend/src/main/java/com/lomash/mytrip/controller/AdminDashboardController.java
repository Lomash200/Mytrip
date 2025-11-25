package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.admin.DashboardStatsResponse;
import com.lomash.mytrip.service.AdminDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService service;

    public AdminDashboardController(AdminDashboardService service) {
        this.service = service;
    }

    // default: last 7 days, 10 recent, top 5 hotels
    @GetMapping("/stats")
    public DashboardStatsResponse getStats(
            @RequestParam(defaultValue = "7") int days,
            @RequestParam(defaultValue = "10") int recentLimit,
            @RequestParam(defaultValue = "5") int popularLimit) {

        return service.getDashboardStats(days, recentLimit, popularLimit);
    }
}
