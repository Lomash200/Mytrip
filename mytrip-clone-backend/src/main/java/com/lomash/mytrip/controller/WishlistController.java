package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.wishlist.WishlistResponse;
import com.lomash.mytrip.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/{type}/{targetId}")
    public ResponseEntity<WishlistResponse> add(
            @PathVariable String type,
            @PathVariable Long targetId) {
        return ResponseEntity.ok(service.addToWishlist(type, targetId));
    }

    @DeleteMapping("/{type}/{targetId}")
    public ResponseEntity<?> remove(
            @PathVariable String type,
            @PathVariable Long targetId) {
        service.removeFromWishlist(type, targetId);
        return ResponseEntity.ok("Removed from wishlist");
    }

    @GetMapping
    public List<WishlistResponse> list() {
        return service.getMyWishlist();
    }
}
