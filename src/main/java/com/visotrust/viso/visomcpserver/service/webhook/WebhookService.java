package com.visotrust.viso.visomcpserver.service.webhook;

import com.visotrust.viso.visomcpserver.model.webhook.Webhook;
import com.visotrust.viso.visomcpserver.model.webhook.WebhookCreateUpdateRequest;
import com.visotrust.viso.visomcpserver.service.ApiService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebhookService {

    private static final String WEBHOOKS_API_PATH = "/api/v1/webhooks";
    private final ApiService apiService;

    public WebhookService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(name = "get_all_webhooks",
            description = "Get all webhooks.")
    public List<Webhook> getAllWebhooks() {
        return apiService.getList(WEBHOOKS_API_PATH, Webhook.class);
    }

    @Tool(name = "get_webhook",
            description = "Get a webhook configuration by id.")
    public Webhook getWebhookById(Long id) {
        return apiService.get(WEBHOOKS_API_PATH + "/" + id, Webhook.class);
    }

    @Tool(name = "create_webhook_configuration",
            description = "Create a webhook configuration.")
    public Webhook createWebhook(WebhookCreateUpdateRequest request) {
        return apiService.post(WEBHOOKS_API_PATH, request, Webhook.class);
    }

    @Tool(name = "update_webhook_configuration",
            description = "Update a webhook configuration.")
    public Webhook updateWebhook(WebhookCreateUpdateRequest request) {
        return apiService.put(WEBHOOKS_API_PATH, request, Webhook.class);
    }

    @Tool(name = "delete_webhook_configuration",
            description = "Delete a webhook configuration.")
    public void deleteWebhook(Long id) {
        apiService.delete(WEBHOOKS_API_PATH + "/" + id);
    }
}