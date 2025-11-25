package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.wishlist.WishlistResponse;

import java.util.List;

public interface WishlistService {

    WishlistResponse addToWishlist(String type, Long targetId);

    void removeFromWishlist(String type, Long targetId);

    List<WishlistResponse> getMyWishlist();
}
