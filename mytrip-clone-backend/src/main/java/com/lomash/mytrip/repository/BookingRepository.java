package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Booking;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.BookingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // ========================= USER BOOKINGS =========================
    List<Booking> findByUser(User user);


    // ========================= COUNT BY STATUS ======================
    long countByStatus(BookingStatus status);


    // ========================= TOTAL REVENUE (Confirmed) ============
    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Booking b WHERE b.status = :status")
    double sumTotalAmountByStatus(@Param("status") BookingStatus status);


    // ========================= RECENT BOOKINGS ======================
    @Query("SELECT b FROM Booking b ORDER BY b.createdAt DESC")
    List<Booking> findRecentBookings(Pageable pageable);


    // ========================= POPULAR HOTELS =======================
    @Query("""
            SELECT b.hotel.id AS hotelId,
                   b.hotel.name AS hotelName,
                   COUNT(b) AS totalBookings
            FROM Booking b
            WHERE b.status = com.lomash.mytrip.entity.enums.BookingStatus.CONFIRMED
              AND b.hotel IS NOT NULL
            GROUP BY b.hotel.id, b.hotel.name
            ORDER BY totalBookings DESC
            """)
    List<Object[]> findTopHotelsByBookingCount(Pageable pageable);


    // ========================= DAILY REVENUE ========================
    @Query("""
            SELECT FUNCTION('date', b.createdAt) AS bookingDate,
                   COALESCE(SUM(b.totalAmount), 0) AS revenue
            FROM Booking b
            WHERE b.status = com.lomash.mytrip.entity.enums.BookingStatus.CONFIRMED
              AND b.createdAt >= :from
              AND b.createdAt < :to
            GROUP BY FUNCTION('date', b.createdAt)
            ORDER BY bookingDate
            """)
    List<Object[]> findDailyRevenue(@Param("from") LocalDateTime from,
                                    @Param("to") LocalDateTime to);
}
