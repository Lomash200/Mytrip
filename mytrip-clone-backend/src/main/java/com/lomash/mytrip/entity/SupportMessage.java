package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SupportMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private boolean fromAdmin;   // false → user | true → admin

    private LocalDateTime time;

    @ManyToOne
    private SupportTicket ticket;
}
