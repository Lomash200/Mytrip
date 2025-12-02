package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code; // "WELCOME200"

    private Double discount; // static discount amount
    private Double minAmount; // minimum booking amount required

    private LocalDateTime expiryDate;

    @Builder.Default  // ✅ FIX: Add this annotation
    private Boolean active = true;
}