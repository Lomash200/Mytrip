package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final FileStorageService fileStorageService;

    public KycController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ApiResponse<?> uploadKyc(@RequestParam("file") MultipartFile file) {

        try {
            String fileName = fileStorageService.saveFile(file, "kyc");
            return ApiResponse.ok("KYC uploaded successfully", fileName);

        } catch (Exception e) {
            return ApiResponse.error("KYC upload failed: " + e.getMessage());
        }
    }
}
