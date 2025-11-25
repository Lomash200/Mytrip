package com.lomash.mytrip.dto.wishlist;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {
    private Long id;
    private String type;       // "HOTEL" or "FLIGHT"
    private Long targetId;
}
