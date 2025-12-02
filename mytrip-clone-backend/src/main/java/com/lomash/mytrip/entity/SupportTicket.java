package com.lomash.mytrip.entity;

import com.lomash.mytrip.entity.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Enumerated(EnumType.STRING)
    @Builder.Default  // ✅ FIX: Add this annotation
    private TicketStatus status = TicketStatus.OPEN;

    private LocalDateTime createdAt;

    @ManyToOne
    private User user;  // who created the ticket
}