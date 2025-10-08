/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.intelligencereport;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record CreateSecurityScorecardReportRequest(
        @ToolParam(description = "The vendor's primary domain name") @NotNull String vendorDomain,
        @ToolParam(description = "The date/time the report was generated") @NotNull
                ZonedDateTime reportDate,
        @ToolParam(
                        required = false,
                        description = "Optional link to the report in the provider's UI")
                @Nullable
                String link,
        @ToolParam(description = "SecurityScorecard letter grade (e.g., A, B, C)") @NotNull
                String grade,
        @ToolParam(required = false, description = "Domain associated with the scorecard entity")
                @Nullable
                String domain,
        @ToolParam(
                        required = false,
                        description = "Numeric score from SecurityScorecard, if available")
                @Nullable
                Integer score) {}
