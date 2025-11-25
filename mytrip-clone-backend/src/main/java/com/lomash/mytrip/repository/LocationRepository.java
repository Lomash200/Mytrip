package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findTop5ByNameStartingWithIgnoreCase(String prefix, Pageable pageable);

    List<Location> findTop5ByNameContainingIgnoreCase(String q, Pageable pageable);
}
