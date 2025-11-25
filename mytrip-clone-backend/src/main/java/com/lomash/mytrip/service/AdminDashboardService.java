package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.admin.DashboardStatsResponse;

public interface AdminDashboardService {
    DashboardStatsResponse getDashboardStats(int daysForRevenue, int recentLimit, int popularLimit);
}
