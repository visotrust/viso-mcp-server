/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;
import org.springframework.ai.tool.annotation.ToolParam;

public record ThirdPartyContactUpdateRequest(
        @ToolParam(description = "ID of the relationship to update") @NotNull Long relationshipId,
        @ToolParam(description = "Email address of the third party contact") @NotNull String email,
        @ToolParam(description = "First name of the third party contact") @NotNull String firstName,
        @ToolParam(description = "Last name of the third party contact") @NotNull
                String lastName) {}
