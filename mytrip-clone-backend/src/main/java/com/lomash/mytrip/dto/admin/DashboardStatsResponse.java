package com.lomash.mytrip.dto.admin;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsResponse {
    private long totalUsers;
    private long totalHotels;
    private long totalRooms;
    private long totalBookings;
    private double totalRevenue;

    private long pendingBookings;
    private long confirmedBookings;
    private long cancelledBookings;

    private List<RecentBookingDto> recentBookings;
    private List<PopularHotelDto> popularHotels;
    private List<DailyRevenueDto> dailyRevenue;
}
