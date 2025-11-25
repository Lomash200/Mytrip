package com.lomash.mytrip.repository;

import com.lomash.mytrip.entity.SupportTicket;
import com.lomash.mytrip.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByUser(User user);
}
