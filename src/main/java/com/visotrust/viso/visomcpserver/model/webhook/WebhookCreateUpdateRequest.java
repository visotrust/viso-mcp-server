/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.webhook;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record WebhookCreateUpdateRequest(
        @Nullable Long id,
        @NotNull WebhookServiceType serviceType,
        @NotNull String webhookUrl,
        @Nullable String description,
        @NotNull List<WebhookEventType> eventTypes) {}
