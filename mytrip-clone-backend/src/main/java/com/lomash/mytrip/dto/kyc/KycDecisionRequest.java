package com.lomash.mytrip.dto.kyc;

import lombok.Data;

@Data
public class KycDecisionRequest {
    private String status;   // APPROVED / REJECTED
    private String remark;

    public boolean isApproved() {
        return "APPROVED".equalsIgnoreCase(status);
    }
}
