package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Page<Hotel> findByLocationId(Long locationId, Pageable pageable);

    List<Hotel> findTop5ByNameStartingWithIgnoreCase(String prefix, Pageable pageable);

    List<Hotel> findTop5ByNameContainingIgnoreCase(String q, Pageable pageable);
}
