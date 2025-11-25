package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;     // Razorpay/Stripe Order ID
    private String paymentId;   // Razorpay/Stripe Payment ID
    private String signature;   // Signature from webhook

    private double amount;      // Amount Paid
    private String status;      // CREATED / SUCCESS / FAILED

    @ManyToOne
    private Booking booking;    // Link to your Booking table
}
