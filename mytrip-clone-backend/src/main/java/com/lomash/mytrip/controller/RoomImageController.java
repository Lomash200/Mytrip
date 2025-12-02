package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rooms")
public class RoomImageController {

    private final FileStorageService fileStorageService;

    public RoomImageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/{roomId}/upload-image")
    public ApiResponse<?> uploadRoomImage(
            @PathVariable Long roomId,
            @RequestParam("image") MultipartFile file
    ) {

        try {
            String fileName = fileStorageService.saveFile(file, "room-images");
            return ApiResponse.ok("Room image uploaded", fileName);

        } catch (Exception e) {
            return ApiResponse.error("Failed: " + e.getMessage());
        }
    }
}
