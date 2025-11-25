package com.lomash.mytrip.service.impl;

import com.lomash.mytrip.dto.kyc.*;
import com.lomash.mytrip.entity.KycDocument;
import com.lomash.mytrip.entity.User;
import com.lomash.mytrip.entity.enums.KycStatus;
import com.lomash.mytrip.exception.ApiException;
import com.lomash.mytrip.repository.KycRepository;
import com.lomash.mytrip.service.AuthService;
import com.lomash.mytrip.service.KycService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final AuthService authService;

    public KycServiceImpl(KycRepository kycRepository, AuthService authService) {
        this.kycRepository = kycRepository;
        this.authService = authService;
    }

    // Convert Entity to DTO
    private KycResponse map(KycDocument d) {
        return KycResponse.builder()
                .id(d.getId())
                .documentType(d.getDocumentType())
                .documentNumber(d.getDocumentNumber())
                .fileUrl(d.getFileUrl())
                .status(d.getStatus().name())
                .adminRemark(d.getAdminRemark())
                .uploadedAt(d.getUploadedAt() != null ? d.getUploadedAt().toString() : null)
                .reviewedAt(d.getReviewedAt() != null ? d.getReviewedAt().toString() : null)

                .build();
    }

    @Override
    public KycResponse uploadDocument(KycUploadRequest request) {

        User user = authService.getLoggedUser();

        KycDocument doc = KycDocument.builder()
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .fileUrl(request.getFileUrl())
                .status(KycStatus.PENDING)
                .uploadedAt(LocalDateTime.now())
                .user(user)
                .build();

        kycRepository.save(doc);
        return map(doc);
    }

    @Override
    public List<KycResponse> myDocuments() {
        User user = authService.getLoggedUser();
        return kycRepository.findByUser(user)
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public KycResponse adminReview(Long id, KycDecisionRequest req) {
        KycDocument doc = kycRepository.findById(id)
                .orElseThrow(() -> new ApiException("Document not found"));

        // FIXED: status check based on string
        doc.setStatus(
                req.getStatus().equalsIgnoreCase("APPROVED")
                        ? KycStatus.APPROVED
                        : KycStatus.REJECTED
        );

        doc.setAdminRemark(req.getRemark());
        doc.setReviewedAt(LocalDateTime.now());

        kycRepository.save(doc);
        return map(doc);
    }

    @Override
    public List<KycResponse> getAllPending() {
        return kycRepository.findByStatus(KycStatus.PENDING)
                .stream()
                .map(this::map)
                .toList();
    }
}
