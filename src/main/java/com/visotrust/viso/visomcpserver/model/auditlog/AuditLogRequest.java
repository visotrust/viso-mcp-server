/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.auditlog;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record AuditLogRequest(
        @ToolParam(description = "Start date and time of the audit log query") @NotNull
                LocalDateTime start,
        @ToolParam(description = "End date and time of the audit log query") @NotNull
                LocalDateTime end) {}
