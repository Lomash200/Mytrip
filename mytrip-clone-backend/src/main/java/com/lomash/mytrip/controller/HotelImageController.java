package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/hotels")
public class HotelImageController {

    private final FileStorageService fileStorageService;

    public HotelImageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/{hotelId}/upload-image")
    public ApiResponse<?> uploadHotelImage(
            @PathVariable Long hotelId,
            @RequestParam("image") MultipartFile file
    ) {
        try {
            String fileName = fileStorageService.saveFile(file, "hotel-images");
            return ApiResponse.ok("Hotel image uploaded", fileName);

        } catch (Exception e) {
            return ApiResponse.error("Failed: " + e.getMessage());
        }
    }
}
