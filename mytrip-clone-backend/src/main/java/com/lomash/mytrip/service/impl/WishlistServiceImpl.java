package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.wishlist.WishlistResponse;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.WishlistItem;
import com.lomash.mytrip.entity.enums.WishlistType;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.WishlistRepository;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.WishlistService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository,
                               UserRepository userRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new ApiException("User not found"));
    }

    @Override
    public WishlistResponse addToWishlist(String type, Long targetId) {

        User user = getCurrentUser();

        WishlistType wishlistType = WishlistType.valueOf(type.toUpperCase());

        // prevent duplicates
        if (wishlistRepository.findByUserAndTypeAndTargetId(user, wishlistType, targetId).isPresent()) {
            throw new ApiException("Already in wishlist");
        }

        WishlistItem item = WishlistItem.builder()
                .user(user)
                .type(wishlistType)
                .targetId(targetId)
                .build();

        wishlistRepository.save(item);

        return WishlistResponse.builder()
                .id(item.getId())
                .type(type)
                .targetId(targetId)
                .build();
    }

    @Override
    public void removeFromWishlist(String type, Long targetId) {
        User user = getCurrentUser();
        WishlistType wishlistType = WishlistType.valueOf(type.toUpperCase());
        wishlistRepository.deleteByUserAndTypeAndTargetId(user, wishlistType, targetId);
    }

    @Override
    public List<WishlistResponse> getMyWishlist() {
        User user = getCurrentUser();
        return wishlistRepository.findByUser(user)
                .stream()
                .map(item -> WishlistResponse.builder()
                        .id(item.getId())
                        .type(item.getType().name())
                        .targetId(item.getTargetId())
                        .build())
                .toList();
    }
}
