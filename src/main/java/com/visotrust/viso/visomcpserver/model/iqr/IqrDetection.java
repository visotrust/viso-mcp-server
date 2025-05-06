package com.visotrust.viso.visomcpserver.model.iqr;

import jakarta.annotation.Nullable;

public record IqrDetection(
    String artifactName,
    @Nullable Double confidence,
    @Nullable String notes,
    @Nullable String controlIds,
    @Nullable String sectionName,
    @Nullable String viewerPage,
    @Nullable String managementResponse,
    IqrDetectionType detectionType
) {}
