package com.visotrust.viso.visomcpserver.service.iqr;

import com.visotrust.viso.visomcpserver.model.iqr.IqrRelationshipRequest;
import com.visotrust.viso.visomcpserver.model.iqr.IqrResponse;
import com.visotrust.viso.visomcpserver.model.iqr.IqrTrustRequest;
import com.visotrust.viso.visomcpserver.service.ApiService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class IqrService {

    private static final String TRUST_PROFILE_IQR_API_PATH = "/api/v1/trust-profile/ask-iqr";
    private static final String RELATIONSHIP_IQR_API_PATH = "/api/v1/relationship/ask-iqr";
    private final ApiService apiService;

    public IqrService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(name = "ask_trust_center",
            description = "Ask questions about your AI Trust Center and receive AI-generated responses.")
    public IqrResponse askTrustProfileIqr(IqrTrustRequest request) {
        return apiService.post(TRUST_PROFILE_IQR_API_PATH, request, IqrResponse.class);
    }

    @Tool(name = "ask_relationship",
            description = "Ask questions about a specific relationship and receive AI-generated responses.")
    public IqrResponse askRelationshipIqr(IqrRelationshipRequest request) {
        return apiService.post(RELATIONSHIP_IQR_API_PATH, request, IqrResponse.class);
    }
}