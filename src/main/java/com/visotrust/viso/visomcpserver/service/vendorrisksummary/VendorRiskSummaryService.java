/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.vendorrisksummary;

import com.visotrust.viso.visomcpserver.model.vendorrisksummary.VendorRiskSummaryResponse;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.annotation.Nullable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class VendorRiskSummaryService {

    private static final String VENDORS_API_PATH = "/api/v1/vendors";
    private final ApiService apiService;

    public VendorRiskSummaryService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_vendor_risk_summary_by_id",
            description =
                    "Get the aggregated risk summary for a vendor by its vendor ID. Includes overall risk level/score, per-relationship risk entries, and control-domain summaries across all of your relationships with that vendor.")
    public VendorRiskSummaryResponse getVendorRiskSummaryById(
            @ToolParam(description = "The vendor ID") Long id) {
        return apiService.get(
                String.format("%s/%d/risk-summary", VENDORS_API_PATH, id),
                VendorRiskSummaryResponse.class);
    }

    @Tool(
            name = "get_vendor_risk_summary_by_name_or_domain",
            description =
                    "Get the aggregated risk summary for a vendor by vendor name or domain. Provide at least one of name or domain.")
    public VendorRiskSummaryResponse getVendorRiskSummaryByNameOrDomain(
            @ToolParam(required = false, description = "Vendor name") @Nullable String name,
            @ToolParam(required = false, description = "Vendor domain, e.g. example.com") @Nullable
                    String domain) {
        List<String> params = new ArrayList<>();
        if (name != null && !name.isBlank()) {
            params.add("name=" + URLEncoder.encode(name, StandardCharsets.UTF_8));
        }
        if (domain != null && !domain.isBlank()) {
            params.add("domain=" + URLEncoder.encode(domain, StandardCharsets.UTF_8));
        }
        String path = VENDORS_API_PATH + "/risk-summary";
        if (!params.isEmpty()) {
            path += "?" + String.join("&", params);
        }
        return apiService.get(path, VendorRiskSummaryResponse.class);
    }
}
