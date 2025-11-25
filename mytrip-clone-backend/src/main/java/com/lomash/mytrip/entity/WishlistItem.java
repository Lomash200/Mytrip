package com.lomash.mytrip.entity;

import com.lomash.mytrip.entity.enums.WishlistType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WishlistType type;   // HOTEL or FLIGHT

    private Long targetId;       // hotelId or flightId

    @ManyToOne
    private User user;
}
