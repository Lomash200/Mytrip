package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.refund.RefundRequest;
import com.lomash.mytrip.dto.refund.RefundResponse;

public interface RefundService {
    RefundResponse initiateRefund(RefundRequest request);
}
