/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.webhook;

import jakarta.annotation.Nullable;
import java.util.List;

public record Webhook(
        @Nullable Long id,
        String webhookUrl,
        WebhookServiceType serviceType,
        @Nullable String description,
        List<String> eventTypes) {}
