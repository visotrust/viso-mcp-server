/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.chat;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import org.springframework.ai.tool.annotation.ToolParam;

public record VisoAgentClientChatRequest(
        @ToolParam(
                        required = false,
                        description =
                                "Optional conversation ID to continue an existing conversation. Omit to start a new conversation.")
                @Nullable
                UUID conversationId,
        @ToolParam(description = "The user message to send to the VISO agent") @NotBlank
                String message) {}
