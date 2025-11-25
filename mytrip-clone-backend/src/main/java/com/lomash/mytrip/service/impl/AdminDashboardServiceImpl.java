package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.admin.*;
import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.Hotel;
import com.lomash.mytrip.entity.enums.BookingStatus;
import com.lomash.mytrip.repository.*;
import com.lomash.mytrip.service.AdminDashboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public AdminDashboardServiceImpl(UserRepository userRepository,
                                     HotelRepository hotelRepository,
                                     RoomRepository roomRepository,
                                     BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public DashboardStatsResponse getDashboardStats(int daysForRevenue, int recentLimit, int popularLimit) {

        long totalUsers = userRepository.count();
        long totalHotels = hotelRepository.count();
        long totalRooms = roomRepository.count();
        long totalBookings = bookingRepository.count();

        double totalRevenue = bookingRepository.sumTotalAmountByStatus(BookingStatus.CONFIRMED);

        long pending = bookingRepository.countByStatus(BookingStatus.PENDING);
        long confirmed = bookingRepository.countByStatus(BookingStatus.CONFIRMED);
        long cancelled = bookingRepository.countByStatus(BookingStatus.CANCELLED);

        // recent bookings
        List<Booking> recent = bookingRepository.findRecentBookings(PageRequest.of(0, Math.max(5, recentLimit)));
        List<RecentBookingDto> recentDtos = new ArrayList<>();
        for (Booking b : recent) {
            RecentBookingDto r = RecentBookingDto.builder()
                    .bookingId(b.getId())
                    .username(b.getUser() != null ? b.getUser().getUsername() : "N/A")
                    .title(b.getHotel() != null ? b.getHotel().getName() : "N/A")
                    .status(b.getStatus() != null ? b.getStatus().name() : "N/A")
                    .amount(b.getTotalAmount())
                    .createdAt(b.getCreatedAt() != null ? b.getCreatedAt().toString() : "")
                    .build();
            recentDtos.add(r);
        }

        // popular hotels
        List<Object[]> popularRaw = bookingRepository.findTopHotelsByBookingCount(PageRequest.of(0, popularLimit));
        List<PopularHotelDto> popular = new ArrayList<>();
        for (Object[] row : popularRaw) {
            Long hid = ((Number) row[0]).longValue();
            String hname = (String) row[1];
            Long cnt = ((Number) row[2]).longValue();
            popular.add(new PopularHotelDto(hid, hname, cnt));
        }

        // daily revenue for last N days
        LocalDate today = LocalDate.now(ZoneOffset.UTC);
        LocalDate fromDate = today.minusDays(daysForRevenue - 1);
        LocalDateTime from = fromDate.atStartOfDay();
        LocalDateTime to = today.plusDays(1).atStartOfDay();

        List<Object[]> revRows = bookingRepository.findDailyRevenue(from, to);
        // map date string -> revenue
        Map<String, Double> revenueMap = new HashMap<>();
        for (Object[] row : revRows) {
            Object d = row[0]; // date
            String dateStr = d.toString();
            Double sum = ((Number) row[1]).doubleValue();
            revenueMap.put(dateStr, sum);
        }

        List<DailyRevenueDto> daily = new ArrayList<>();
        for (int i = daysForRevenue - 1; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            String ds = day.toString();
            daily.add(new DailyRevenueDto(ds, revenueMap.getOrDefault(ds, 0.0)));
        }

        return DashboardStatsResponse.builder()
                .totalUsers(totalUsers)
                .totalHotels(totalHotels)
                .totalRooms(totalRooms)
                .totalBookings(totalBookings)
                .totalRevenue(totalRevenue)
                .pendingBookings(pending)
                .confirmedBookings(confirmed)
                .cancelledBookings(cancelled)
                .recentBookings(recentDtos)
                .popularHotels(popular)
                .dailyRevenue(daily)
                .build();
    }
}
