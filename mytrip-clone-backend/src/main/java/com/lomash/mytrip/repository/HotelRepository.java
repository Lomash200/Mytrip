package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findByCityIgnoreCase(String city, Pageable pageable);

    List<Hotel> findTop5ByNameStartingWithIgnoreCase(String prefix, Pageable pageable);
    List<Hotel> findTop5ByNameContainingIgnoreCase(String q, Pageable pageable);

    List<Hotel> findByCityIgnoreCase(String city);
    List<Hotel> findByNameContainingIgnoreCase(String name);

    @Query("SELECT h FROM Hotel h LEFT JOIN FETCH h.rooms WHERE h.id = :hotelId")
    Hotel findHotelWithRooms(Long hotelId);
}

