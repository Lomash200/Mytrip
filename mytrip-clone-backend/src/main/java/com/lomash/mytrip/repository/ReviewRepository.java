package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByTargetTypeAndTargetIdAndApproved(String targetType, Long targetId, boolean approved);

    List<Review> findByTargetTypeAndTargetId(String targetType, Long targetId);

    List<Review> findByUserId(Long userId);
}
