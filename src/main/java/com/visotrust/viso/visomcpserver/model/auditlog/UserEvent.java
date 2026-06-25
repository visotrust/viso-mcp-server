/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.auditlog;

import java.time.ZonedDateTime;

public record UserEvent(
        UserAuditLogType auditEventType, ZonedDateTime dateTime, String email, Long userId) {}
