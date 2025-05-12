/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AssessmentCreateRequest(
        @NotNull Long relationshipId,
        @NotNull String recipientEmail,
        @NotNull String recipientFirstName,
        @NotNull String recipientLastName,
        @Nullable List<String> publicDocumentUrls,
        @NotNull FollowupType followupType,
        @Nullable RiskLevel followupRiskThreshold,
        @Nullable Boolean aiProcessingOnly) {}
