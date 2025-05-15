/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.iqr;

import jakarta.annotation.Nullable;
import java.util.List;

public record IqrResponse(
        @Nullable String referenceUrl,
        String response,
        @Nullable IqrStatus status,
        @Nullable String controlDomain,
        @Nullable List<IqrDetection> detections) {}
