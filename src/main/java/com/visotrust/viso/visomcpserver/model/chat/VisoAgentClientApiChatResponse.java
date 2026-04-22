/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.chat;

import java.util.UUID;

public record VisoAgentClientApiChatResponse(UUID conversationId, String reply) {}
