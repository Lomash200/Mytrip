package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.review.ReviewRequest;
import com.lomash.mytrip.dto.review.ReviewResponse;
import com.lomash.mytrip.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.addReview(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id,
                                                       @Valid @RequestBody ReviewRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/target/{targetType}/{targetId}")
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable String targetType,
            @PathVariable Long targetId,
            @RequestParam(defaultValue = "true") boolean onlyApproved) {
        return ResponseEntity.ok(reviewService.getReviews(targetType, targetId, onlyApproved));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReviewResponse>> myReviews() {
        return ResponseEntity.ok(reviewService.getMyReviews());
    }

    @GetMapping("/target/{targetType}/{targetId}/avg")
    public ResponseEntity<Double> avgRating(@PathVariable String targetType, @PathVariable Long targetId) {
        return ResponseEntity.ok(reviewService.getAverageRating(targetType, targetId));
    }
}
