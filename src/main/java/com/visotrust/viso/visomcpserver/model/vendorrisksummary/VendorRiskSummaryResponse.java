/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.vendorrisksummary;

import java.time.ZonedDateTime;
import java.util.List;

public record VendorRiskSummaryResponse(
        Long vendorId,
        String vendorName,
        ZonedDateTime evaluatedAt,
        String overallRiskLevel,
        Float overallRiskScore,
        Integer relationshipCount,
        List<RelationshipEntry> relationships,
        List<AggregateControlDomainSummary> aggregateControlDomainSummary,
        String summaryText,
        String emptySummaryReason,
        ZonedDateTime summaryGeneratedAt) {}
