package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.KycDocument;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.KycStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KycRepository extends JpaRepository<KycDocument, Long> {
    List<KycDocument> findByUser(User user);
    List<KycDocument> findByStatus(KycStatus status);
}
