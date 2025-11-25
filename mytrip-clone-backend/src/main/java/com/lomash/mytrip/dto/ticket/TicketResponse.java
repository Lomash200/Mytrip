package com.lomash.mytrip.dto.ticket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private String subject;
    private String status;
    private String createdAt;
}
