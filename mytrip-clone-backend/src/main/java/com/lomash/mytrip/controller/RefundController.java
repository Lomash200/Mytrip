package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.refund.RefundRequest;
import com.lomash.mytrip.dto.refund.RefundResponse;
import com.lomash.mytrip.service.RefundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refund")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping
    public ResponseEntity<RefundResponse> initiate(@RequestBody RefundRequest request) {
        return ResponseEntity.ok(refundService.initiateRefund(request));
    }
}
