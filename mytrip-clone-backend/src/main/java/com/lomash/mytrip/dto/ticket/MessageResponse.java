package com.lomash.mytrip.dto.ticket;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private Long id;
    private String message;
    private boolean fromAdmin;
    private String time;
}
