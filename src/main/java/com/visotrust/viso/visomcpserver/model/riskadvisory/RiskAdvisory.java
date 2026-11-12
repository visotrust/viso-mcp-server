/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.riskadvisory;

import java.time.LocalDate;
import java.util.UUID;

public record RiskAdvisory(
        UUID id,
        Long vendorOrgId,
        String vendorOrgName,
        String vendorOrgDescription,
        String vendorOrgFaviconUrl,
        String type,
        String title,
        String description,
        String url,
        LocalDate publishedDate,
        boolean material,
        String vendorName,
        String productName,
        Double severity,
        String remediation) {}
