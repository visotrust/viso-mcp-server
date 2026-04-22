/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public record LifecycleManagementUpdateRequest(
        @Nullable ArtifactUpdateSettings artifactUpdateSettings,
        @Nullable RecertificationSettings recertificationSettings) {

    public record ArtifactUpdateSettings(@NotNull RecertificationType artifactUpdateType) {}

    public record RecertificationSettings(
            @NotNull RecertificationType recertificationType,
            @NotNull ZonedDateTime recertificationDate,
            @NotNull ReviewFrequency reviewFrequency) {}
}
