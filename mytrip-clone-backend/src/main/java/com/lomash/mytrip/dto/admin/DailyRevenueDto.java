package com.lomash.mytrip.dto.admin;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyRevenueDto {
    private String date; // YYYY-MM-DD
    private double revenue;
}
