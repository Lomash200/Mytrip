package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // targetType: HOTEL or FLIGHT
    @Column(nullable = false)
    private String targetType;

    @Column(nullable = false)
    private Long targetId;

    @ManyToOne
    private User user;

    private int rating; // 1..5

    private String title;

    @Column(length = 2000)
    private String comment;

    private boolean approved = true; // moderation flag

    private Instant createdAt;
    private Instant updatedAt;
}
