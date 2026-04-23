/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipOnboardRequest(
        @ToolParam(
                        required = false,
                        description = "Optional approval summary recorded at onboarding time")
                @Nullable
                String approvalSummary,
        @ToolParam(
                        required = false,
                        description =
                                "Optional lifecycle management settings to apply during onboarding (artifact update and recertification)")
                @Nullable
                LifecycleManagementUpdateRequest lifecycleManagementUpdateRequest) {}
