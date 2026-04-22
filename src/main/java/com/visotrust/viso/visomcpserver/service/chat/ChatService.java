/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.chat;

import com.visotrust.viso.visomcpserver.model.chat.VisoAgentClientApiChatResponse;
import com.visotrust.viso.visomcpserver.model.chat.VisoAgentClientChatRequest;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.validation.Valid;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class ChatService {

    private static final String CHAT_API_PATH = "/api/v1/chat";
    private final ApiService apiService;

    public ChatService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "chat_with_viso_agent",
            description =
                    "Send a message to the VISO TRUST agent and receive a reply. Pass a conversationId to continue an existing conversation or omit it to start a new one.")
    public VisoAgentClientApiChatResponse chat(@Valid VisoAgentClientChatRequest request) {
        return apiService.post(CHAT_API_PATH, request, VisoAgentClientApiChatResponse.class);
    }
}
