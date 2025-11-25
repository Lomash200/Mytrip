package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.kyc.KycDecisionRequest;
import com.lomash.mytrip.dto.kyc.KycResponse;
import com.lomash.mytrip.service.KycService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/kyc")
public class AdminKycController {

    private final KycService kycService;

    public AdminKycController(KycService kycService) {
        this.kycService = kycService;
    }

    // 🔹 Get all pending KYC documents
    @GetMapping("/pending")
    public List<KycResponse> getPending() {
        return kycService.getAllPending();
    }

    // 🔹 Approve / Reject a KYC document
    @PutMapping("/{id}/review")
    public KycResponse review(
            @PathVariable Long id,
            @RequestBody KycDecisionRequest request
    ) {
        return kycService.adminReview(id, request);
    }
}
