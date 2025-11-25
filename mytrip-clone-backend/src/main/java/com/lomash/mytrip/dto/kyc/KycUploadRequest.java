package com.lomash.mytrip.dto.kyc;

import lombok.Data;

@Data
public class KycUploadRequest {
    private String documentType;      // Aadhaar / PAN / Passport
    private String documentNumber;
    private String fileUrl;           // uploaded file URL (S3/local)
}
