package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;
    private String roomType;

    @Builder.Default
    private int capacity = 2;

    @Builder.Default
    private int maxGuests = 2;

    private double pricePerNight;
    private String description;

    @Builder.Default
    private int availabilityCount = 1;

    private String imageUrl;

    @ElementCollection
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}