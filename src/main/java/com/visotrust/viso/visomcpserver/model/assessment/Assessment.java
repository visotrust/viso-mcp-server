/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import com.visotrust.viso.visomcpserver.model.common.VisoUser;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

public record Assessment(
        Long id,
        VisoUser sentBy,
        boolean aiProcessingOnly,
        String sentToFirstName,
        String sentToLastName,
        String sentToEmail,
        ZonedDateTime createdDate,
        ZonedDateTime updatedDate,
        ZonedDateTime phaseDate,
        ZonedDateTime completedDate,
        ZonedDateTime expirationDate,
        AssessmentStatus status,
        List<AssessmentStatusHistory> statusHistories,
        String assessmentType,
        FollowupType followupType,
        RiskLevel followupRiskThreshold,
        FollowupTimeline followupTimeline,
        CollectionTimeline collectionTimeline,
        NoVendorResponseAction noVendorResponseAction,
        boolean requestedArtifactCollection,
        Set<AssessmentRecommendation> recommendations) {}
