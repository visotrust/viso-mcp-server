/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.vendorrisksummary;

import java.util.List;

public record AggregateControlDomainSummary(
        String domain, String highestRiskLevel, List<Long> affectedRelationships) {}
