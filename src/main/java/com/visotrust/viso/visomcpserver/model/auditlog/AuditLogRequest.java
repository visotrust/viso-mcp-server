package com.visotrust.viso.visomcpserver.model.auditlog;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AuditLogRequest(
    @NotNull LocalDateTime start,
    @NotNull LocalDateTime end
) {}
