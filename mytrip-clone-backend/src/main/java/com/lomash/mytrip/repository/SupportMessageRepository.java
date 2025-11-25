package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.SupportMessage;
import com.lomash.mytrip.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, Long> {
    List<SupportMessage> findByTicketOrderByTimeAsc(SupportTicket ticket);
}
