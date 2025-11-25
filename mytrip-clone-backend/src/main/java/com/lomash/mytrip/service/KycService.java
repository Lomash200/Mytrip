package com.lomash.mytrip.service;

import com.lomash.mytrip.dto.kyc.*;

import java.util.List;

public interface KycService {

    KycResponse uploadDocument(KycUploadRequest request);

    List<KycResponse> myDocuments();

    KycResponse adminReview(Long id, KycDecisionRequest req);

    List<KycResponse> getAllPending();
}
