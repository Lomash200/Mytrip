package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.review.ReviewResponse;
import com.lomash.mytrip.service.ReviewService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReviewController {

    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @GetMapping
    public List<ReviewResponse> allReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id, @RequestParam(defaultValue = "true") boolean approve) {
        reviewService.approveReview(id, approve);
        return "OK";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "Deleted";
    }
}
