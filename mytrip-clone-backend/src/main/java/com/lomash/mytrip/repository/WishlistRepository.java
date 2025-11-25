package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.WishlistItem;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.WishlistType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUser(User user);

    Optional<WishlistItem> findByUserAndTypeAndTargetId(User user, WishlistType type, Long targetId);

    void deleteByUserAndTypeAndTargetId(User user, WishlistType type, Long targetId);
}
