package com.visotrust.viso.visomcpserver.service.auditlog;

import com.visotrust.viso.visomcpserver.model.auditlog.AuditLogRequest;
import com.visotrust.viso.visomcpserver.model.auditlog.UserEvent;
import com.visotrust.viso.visomcpserver.service.ApiService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogService {

    private static final String AUDIT_LOG_API_PATH = "/api/v1/audit-log/user-events";
    private final ApiService apiService;

    public AuditLogService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(name = "get_user_audit_log_events",
            description = "Get the audit log events for your organization in the time frame, limited to 500 records")
    public List<UserEvent> getUserAuditLogEvents(AuditLogRequest request) {
        return apiService.post(AUDIT_LOG_API_PATH, request,
                new ParameterizedTypeReference<>() {
                });
    }
}