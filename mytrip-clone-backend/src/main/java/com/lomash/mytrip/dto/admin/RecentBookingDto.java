package com.lomash.mytrip.dto.admin;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentBookingDto {
    private Long bookingId;
    private String username;
    private String title; // hotel name or flight summary
    private String status;
    private double amount;
    private String createdAt; // ISO string
}
