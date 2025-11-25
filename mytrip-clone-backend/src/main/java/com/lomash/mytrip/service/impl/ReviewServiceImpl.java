package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.review.ReviewRequest;
import com.lomash.mytrip.dto.review.ReviewResponse;
import com.lomash.mytrip.entity.Review;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.ReviewRepository;
import com.lomash.mytrip.repository.UserRepository;
import com.lomash.mytrip.service.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) throw new ApiException("Unauthenticated");
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username))
                .orElseThrow(() -> new ApiException("User not found"));
    }

    private ReviewResponse mapToDto(Review r) {
        return ReviewResponse.builder()
                .id(r.getId())
                .targetType(r.getTargetType())
                .targetId(r.getTargetId())
                .userId(r.getUser() != null ? r.getUser().getId() : null)
                .username(r.getUser() != null ? r.getUser().getUsername() : null)
                .rating(r.getRating())
                .title(r.getTitle())
                .comment(r.getComment())
                .approved(r.isApproved())
                .createdAt(r.getCreatedAt())
                .build();
    }

    @Override
    @Transactional
    public ReviewResponse addReview(ReviewRequest request) {
        User user = getCurrentUserEntity();

        Review review = Review.builder()
                .targetType(request.getTargetType())
                .targetId(request.getTargetId())
                .user(user)
                .rating(request.getRating())
                .title(request.getTitle())
                .comment(request.getComment())
                .approved(true) // set true or false based on auto-moderation policy
                .createdAt(Instant.now())
                .build();

        review = reviewRepository.save(review);
        return mapToDto(review);
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException("Review not found"));

        User current = getCurrentUserEntity();
        if (!review.getUser().getId().equals(current.getId())) {
            throw new ApiException("Not authorized to update this review");
        }

        review.setRating(request.getRating());
        review.setTitle(request.getTitle());
        review.setComment(request.getComment());
        review.setUpdatedAt(Instant.now());
        review = reviewRepository.save(review);
        return mapToDto(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException("Review not found"));
        User current = getCurrentUserEntity();
        // allow delete if owner or admin (admin handled in controller via PreAuthorize)
        if (!review.getUser().getId().equals(current.getId())) {
            throw new ApiException("Not authorized to delete this review");
        }
        reviewRepository.delete(review);
    }

    @Override
    public List<ReviewResponse> getReviews(String targetType, Long targetId, boolean onlyApproved) {
        List<Review> list = onlyApproved ?
                reviewRepository.findByTargetTypeAndTargetIdAndApproved(targetType, targetId, true) :
                reviewRepository.findByTargetTypeAndTargetId(targetType, targetId);
        return list.stream().map(this::mapToDto).toList();
    }

    @Override
    public double getAverageRating(String targetType, Long targetId) {
        List<Review> list = reviewRepository.findByTargetTypeAndTargetIdAndApproved(targetType, targetId, true);
        if (list.isEmpty()) return 0.0;
        double sum = list.stream().mapToDouble(Review::getRating).sum();
        return Math.round((sum / list.size()) * 10.0) / 10.0; // 1 decimal
    }

    @Override
    public List<ReviewResponse> getMyReviews() {
        User user = getCurrentUserEntity();
        List<Review> list = reviewRepository.findByUserId(user.getId());
        return list.stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    @Transactional
    public void approveReview(Long reviewId, boolean approve) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ApiException("Review not found"));
        review.setApproved(approve);
        review.setUpdatedAt(Instant.now());
        reviewRepository.save(review);
    }
}
