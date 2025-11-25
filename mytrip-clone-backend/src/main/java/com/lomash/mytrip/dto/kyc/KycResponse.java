package com.lomash.mytrip.dto.kyc;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KycResponse {
    private Long id;
    private String documentType;
    private String documentNumber;
    private String status;
    private String fileUrl;
    private String adminRemark;
    private String uploadedAt;
    private String reviewedAt;
}
