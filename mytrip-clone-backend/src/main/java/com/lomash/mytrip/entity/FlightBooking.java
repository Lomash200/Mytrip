package com.lomash.mytrip.entity;

import com.lomash.mytrip.entity.enums.FlightBookingStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "flight_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnr; // unique reference

    private String travelDate; // YYYY-MM-DD
    private int passengers;
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    private FlightBookingStatus status;

    @ManyToOne
    private User user;

    @ManyToOne
    private Flight flight;
}
