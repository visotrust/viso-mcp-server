package com.visotrust.viso.visomcpserver.model.assessment;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import com.visotrust.viso.visomcpserver.model.common.VisoUser;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record Assessment(
    Long id,
    @Nullable LocalDateTime phaseDate,
    @Nullable VisoUser sentBy,
    LocalDateTime createdDate,
    @Nullable String summary,
    LocalDateTime updatedDate,
    String sentToEmail,
    AssessmentStatus status,
    List<AssessmentStatusHistory> statusHistories,
    @Nullable LocalDateTime completedDate,
    FollowupType followupType,
    String assessmentType,
    String sentToFirstName,
    String sentToLastName,
    @Nullable LocalDateTime expirationDate,
    @Nullable RiskLevel followupRiskThreshold
) {}
