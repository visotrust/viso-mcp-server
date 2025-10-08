/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.intelligencereport;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record CreateBitsightReportRequest(
        @ToolParam(description = "The vendor's primary domain name") @NotNull String vendorDomain,
        @ToolParam(description = "The date/time the report was generated") @NotNull
                ZonedDateTime reportDate,
        @ToolParam(
                        required = false,
                        description = "Optional link to the report in the provider's UI")
                @Nullable
                String link,
        @ToolParam(description = "BitSight GUID for the entity") @NotNull String guid,
        @ToolParam(required = false, description = "Custom identifier from BitSight") @Nullable
                String customId,
        @ToolParam(required = false, description = "Display name of the BitSight entity") @Nullable
                String name,
        @ToolParam(required = false, description = "Description of the BitSight entity") @Nullable
                String description,
        @ToolParam(required = false, description = "Primary domain for the BitSight entity")
                @Nullable
                String primaryDomain,
        @ToolParam(required = false, description = "BitSight rating range e.g. 250-900") @Nullable
                String ratingRange,
        @ToolParam(
                        required = false,
                        description = "BitSight rating color (e.g., green, yellow, red)")
                @Nullable
                String ratingColor,
        @ToolParam(required = false, description = "Confidence level of the BitSight rating")
                @Nullable
                String confidence) {}
