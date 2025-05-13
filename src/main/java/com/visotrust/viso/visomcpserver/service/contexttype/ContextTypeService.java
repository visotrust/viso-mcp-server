/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.contexttype;

import com.visotrust.viso.visomcpserver.model.contexttype.ContextTypeView;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class ContextTypeService {

    private static final String CONTEXT_TYPES_API_PATH = "/api/v1/business-cases";
    private final ApiService apiService;

    public ContextTypeService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_business_cases",
            description = "Get all available business cases for your organization.")
    public List<ContextTypeView> getAllBusinessCases() {
        return apiService.getList(CONTEXT_TYPES_API_PATH, ContextTypeView.class);
    }
}
