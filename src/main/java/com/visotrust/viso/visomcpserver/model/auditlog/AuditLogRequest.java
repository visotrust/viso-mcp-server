/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.auditlog;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Set;
import org.springframework.ai.tool.annotation.ToolParam;

public record AuditLogRequest(
        @ToolParam(description = "Start date/time (ISO-8601 with offset) of the audit log query")
                @NotNull
                ZonedDateTime start,
        @ToolParam(description = "End date/time (ISO-8601 with offset) of the audit log query")
                @NotNull
                ZonedDateTime end,
        @ToolParam(
                        required = false,
                        description =
                                "Optional set of event types to filter (e.g. USER_LOGGED_IN, ASSESSMENT_COMPLETED, RELATIONSHIP_CREATED). Leave empty to return all types.")
                @Nullable
                Set<String> eventTypes) {}
