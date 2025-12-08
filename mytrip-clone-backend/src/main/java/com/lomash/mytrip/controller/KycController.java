package com.lomash.mytrip.controller;

import com.lomash.mytrip.common.ApiResponse;
import com.lomash.mytrip.service.FileStorageService;
import com.lomash.mytrip.service.KycService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final FileStorageService fileStorageService;
    private final KycService kycService;

    public KycController(FileStorageService fileStorageService, KycService kycService) {
        this.fileStorageService = fileStorageService;
        this.kycService = kycService;
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

    @GetMapping("/status")
    public ApiResponse<?> getMyKycStatus() {

        try {
            var list = kycService.myDocuments();

            if (list == null || list.isEmpty()) {
                return ApiResponse.ok("No KYC submitted yet", null);
            }

            return ApiResponse.ok("KYC status fetched", list);

        } catch (Exception e) {
            return ApiResponse.error("Failed to fetch status: " + e.getMessage());
        }
    }

}
