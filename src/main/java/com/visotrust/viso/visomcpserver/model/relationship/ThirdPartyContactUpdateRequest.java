/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;

public record ThirdPartyContactUpdateRequest(
        @NotNull Long relationshipId,
        @NotNull String email,
        @NotNull String firstName,
        @NotNull String lastName) {}
