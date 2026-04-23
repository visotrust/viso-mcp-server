/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.auditlog;

import com.fasterxml.jackson.databind.JsonNode;
import com.visotrust.viso.visomcpserver.model.auditlog.AuditLogRequest;
import com.visotrust.viso.visomcpserver.model.auditlog.UserEvent;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private static final String USER_EVENTS_API_PATH = "/api/v1/audit-log/user-events";
    private static final String EVENTS_API_PATH = "/api/v1/audit-log/events";
    private final ApiService apiService;

    public AuditLogService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_user_audit_log_events",
            description =
                    "Get the user-scoped audit log events for your organization in the time frame, limited to 500 records")
    public List<UserEvent> getUserAuditLogEvents(AuditLogRequest request) {
        return apiService.post(
                USER_EVENTS_API_PATH, request, new ParameterizedTypeReference<>() {});
    }

    @Tool(
            name = "get_audit_log_events",
            description =
                    "Get filtered audit log events (user, org, assessment, and relationship events) for your organization in the time frame. Filter by eventTypes. Response items are polymorphic JSON; each has at least `auditEventType` and `dateTime`.")
    public List<JsonNode> getAuditLogEvents(AuditLogRequest request) {
        return apiService.post(EVENTS_API_PATH, request, new ParameterizedTypeReference<>() {});
    }
}
