/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.intelligencereport;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ExternalIntelligenceReport(
        UUID id,
        Long clientOrgId,
        String clientOrgName,
        Long vendorOrgId,
        String vendorOrgName,
        String vendorOrgDescription,
        String vendorOrgFaviconUrl,
        IntelligenceProvider source,
        ZonedDateTime reportDate,
        String link,
        String rating,
        String typeName,
        // Bitsight specific (nullable)
        String guid,
        String customId,
        String name,
        String description,
        String primaryDomain,
        String ratingRange,
        String ratingColor,
        String confidence,
        // SecurityScorecard specific (nullable)
        String grade,
        String domain,
        Integer score) {}
