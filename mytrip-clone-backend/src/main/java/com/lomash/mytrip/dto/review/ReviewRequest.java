package com.lomash.mytrip.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {
    @NotNull
    private String targetType; // HOTEL or FLIGHT

    @NotNull
    private Long targetId;

    @Min(1)
    @Max(5)
    private int rating;

    @NotBlank
    private String title;

    private String comment;
}
