package com.lomash.mytrip.dto.review;

import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long id;
    private String targetType;
    private Long targetId;
    private Long userId;
    private String username;
    private int rating;
    private String title;
    private String comment;
    private boolean approved;
    private Instant createdAt;
}
