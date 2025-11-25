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

    private String roomType;        // SINGLE, DOUBLE, SUITE
    private double pricePerNight;
    private int maxGuests;
    private int availabilityCount;
    private List<String> images = new ArrayList<>();
    @ElementCollection



    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
