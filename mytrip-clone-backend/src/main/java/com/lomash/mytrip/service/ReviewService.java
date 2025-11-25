package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.review.ReviewRequest;
import com.lomash.mytrip.dto.review.ReviewResponse;

import java.util.List;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest request);
    ReviewResponse updateReview(Long reviewId, ReviewRequest request);
    void deleteReview(Long reviewId);
    List<ReviewResponse> getReviews(String targetType, Long targetId, boolean onlyApproved);
    double getAverageRating(String targetType, Long targetId);
    List<ReviewResponse> getMyReviews();
    List<ReviewResponse> getAllReviews(); // admin
    void approveReview(Long reviewId, boolean approve);
}
