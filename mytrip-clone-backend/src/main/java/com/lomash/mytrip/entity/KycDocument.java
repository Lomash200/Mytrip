package com.lomash.mytrip.entity;

import com.lomash.mytrip.entity.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class KycDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String documentType;        // Aadhar, PAN, Passport
    private String documentNumber;

    private String fileUrl;             // uploaded file URL (S3 or local)

    @Enumerated(EnumType.STRING)
    private KycStatus status;

    private String adminRemark;         // rejection/approval remark

    private LocalDateTime uploadedAt;
    private LocalDateTime reviewedAt;

    @ManyToOne
    private User user;
}
