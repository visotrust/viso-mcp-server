/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.riskadvisory;

import com.visotrust.viso.visomcpserver.model.riskadvisory.RiskAdvisory;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class RiskAdvisoryService {

    private final ApiService apiService;

    public RiskAdvisoryService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_risk_advisories_by_vendor",
            description =
                    "Get all risk advisories (security incidents, breaches, vulnerabilities, legal, financial and "
                            + "other risk events) published for a vendor, by vendor id.")
    public List<RiskAdvisory> getRiskAdvisoriesByVendor(
            @ToolParam(description = "The vendor's id (vendorId / vendorOrgId)") Long vendorId) {
        return apiService.getList(
                String.format("/api/v1/vendors/%d/risk-advisories", vendorId), RiskAdvisory.class);
    }

    @Tool(
            name = "get_risk_advisories_by_relationship",
            description =
                    "Get all risk advisories for the vendor of one of the organization's relationships, by relationship id.")
    public List<RiskAdvisory> getRiskAdvisoriesByRelationship(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.getList(
                String.format("/api/v1/relationships/%d/risk-advisories", id), RiskAdvisory.class);
    }
}
