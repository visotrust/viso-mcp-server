/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.intelligencereport;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record CreateRecordedFutureReportRequest(
        @ToolParam(description = "The vendor's primary domain name") @NotNull String vendorDomain,
        @ToolParam(description = "The date/time the report was generated") @NotNull
                ZonedDateTime reportDate,
        @ToolParam(
                        required = false,
                        description = "Optional link to the report in the provider's UI")
                @Nullable
                String link,
        @ToolParam(description = "Recorded Future entity type, e.g. Company") @NotNull
                String entityType,
        @ToolParam(description = "Recorded Future entity identifier") @NotNull String entity,
        @ToolParam(description = "Recorded Future numeric risk score") @NotNull Integer riskScore,
        @ToolParam(description = "Recorded Future risk level label, e.g. Critical/High/Medium/Low")
                @NotNull
                String riskLevel,
        @ToolParam(required = false, description = "Earliest observed date for the entity")
                @Nullable
                ZonedDateTime firstSeen,
        @ToolParam(required = false, description = "Most recent observed date for the entity")
                @Nullable
                ZonedDateTime lastSeen,
        @ToolParam(
                        required = false,
                        description = "Number of Recorded Future rules that have triggered")
                @Nullable
                Integer triggeredRuleCount,
        @ToolParam(
                        required = false,
                        description = "Maximum number of Recorded Future rules evaluated")
                @Nullable
                Integer maxRuleCount,
        @ToolParam(required = false, description = "Optional summary text from Recorded Future")
                @Nullable
                String summary,
        @ToolParam(
                        required = false,
                        description = "Recorded Future criticality label for the entity")
                @Nullable
                String criticalityLabel) {}
