/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;

public record RelationshipOnboardRequest(
        @Nullable String approvalSummary,
        @Nullable LifecycleManagementUpdateRequest lifecycleManagementUpdateRequest) {}
