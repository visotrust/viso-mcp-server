/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.webhook;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

public record WebhookCreateUpdateRequest(
        @ToolParam(description = "The ID of the webhook to update", required = false) @Nullable
                Long id,
        @ToolParam(description = "The type of service this webhook is for") @NotNull
                WebhookServiceType serviceType,
        @ToolParam(description = "The URL that will receive webhook events") @NotNull
                String webhookUrl,
        @ToolParam(description = "Optional description for this webhook", required = false)
                @Nullable
                String description,
        @ToolParam(description = "List of event types that will trigger this webhook") @NotNull
                List<WebhookEventType> eventTypes) {}
