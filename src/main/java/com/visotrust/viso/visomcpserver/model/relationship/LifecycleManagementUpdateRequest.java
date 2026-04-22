/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record LifecycleManagementUpdateRequest(
        @ToolParam(required = false, description = "Settings controlling how artifacts update")
                @Nullable
                ArtifactUpdateSettings artifactUpdateSettings,
        @ToolParam(
                        required = false,
                        description =
                                "Settings controlling recertification type, date, and review frequency")
                @Nullable
                RecertificationSettings recertificationSettings) {

    public record ArtifactUpdateSettings(
            @ToolParam(description = "Artifact update type") @NotNull
                    RecertificationType artifactUpdateType) {}

    public record RecertificationSettings(
            @ToolParam(description = "Recertification type") @NotNull
                    RecertificationType recertificationType,
            @ToolParam(
                            description =
                                    "Date/time (ISO-8601 with offset) when the next recertification should occur")
                    @NotNull
                    ZonedDateTime recertificationDate,
            @ToolParam(description = "How frequently recertification should recur") @NotNull
                    ReviewFrequency reviewFrequency) {}
}
