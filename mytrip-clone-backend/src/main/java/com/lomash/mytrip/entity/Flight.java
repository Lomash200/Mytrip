package com.lomash.mytrip.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String airline;
    private String flightNumber;

    @ManyToOne
    private Location origin;

    @ManyToOne
    private Location destination;

    private String departureTime; // yyyy-MM-dd HH:mm
    private String arrivalTime;

    private double price;
    private int seatsAvailable;
}
