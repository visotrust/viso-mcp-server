/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.intelligencereport;

import java.util.Set;
import java.util.UUID;

/**
 * @param totalCount number of reports submitted
 * @param created ids of the reports created immediately
 * @param backgroundedCount reports queued to be created in the background
 */
public record BulkIntelligenceReportResult(
        int totalCount, Set<UUID> created, int backgroundedCount, boolean backgrounded) {}
