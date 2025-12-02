package com.lomash.mytrip.entity;

import com.lomash.mytrip.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String referenceCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int guests;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    // Payment fields
    private String orderId;
    private String paymentStatus;
    private Instant reservationExpiresAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Hotel hotel;

    @ManyToOne
    private Room room;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.paymentStatus == null) {
            this.paymentStatus = "PENDING_PAYMENT";
        }
        if (this.status == null) {
            this.status = BookingStatus.PENDING;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}