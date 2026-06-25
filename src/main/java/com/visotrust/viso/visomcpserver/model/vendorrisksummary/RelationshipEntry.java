/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.vendorrisksummary;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record RelationshipEntry(
        Long relationshipId,
        UUID productServiceId,
        String productServiceName,
        String assessmentStatus,
        String riskLevel,
        Float riskScore,
        ZonedDateTime lastAssessedAt,
        List<ControlDomainSummary> controlDomainSummary) {}
