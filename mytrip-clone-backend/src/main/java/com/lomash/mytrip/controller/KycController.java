package com.lomash.mytrip.controller;

import com.lomash.mytrip.dto.kyc.*;
import com.lomash.mytrip.service.KycService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kyc")
public class KycController {

    private final KycService service;

    public KycController(KycService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public KycResponse upload(@RequestBody KycUploadRequest req) {
        return service.uploadDocument(req);
    }

    @GetMapping("/my")
    public List<KycResponse> myDocs() {
        return service.myDocuments();
    }
}
