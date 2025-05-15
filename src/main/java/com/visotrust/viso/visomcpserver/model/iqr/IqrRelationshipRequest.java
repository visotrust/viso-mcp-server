/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.iqr;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record IqrRelationshipRequest(
        @NotNull String question, @NotNull Long relationshipId, @Nullable String domain) {}
