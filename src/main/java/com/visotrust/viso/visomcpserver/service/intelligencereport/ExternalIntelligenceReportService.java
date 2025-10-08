/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.intelligencereport;

import com.visotrust.viso.visomcpserver.model.intelligencereport.CreateBitsightReportRequest;
import com.visotrust.viso.visomcpserver.model.intelligencereport.CreateSecurityScorecardReportRequest;
import com.visotrust.viso.visomcpserver.model.intelligencereport.ExternalIntelligenceReport;
import com.visotrust.viso.visomcpserver.model.intelligencereport.IntelligenceProvider;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class ExternalIntelligenceReportService {

    private static final String EXTERNAL_INTELLIGENCE_API_PATH =
            "/api/v1/external-intelligence-reports";
    private final ApiService apiService;

    public ExternalIntelligenceReportService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "create_bitsight_intelligence_report",
            description = "Create a new BitSight intelligence report")
    public ExternalIntelligenceReport createBitsightIntelligenceReport(
            @Valid CreateBitsightReportRequest request) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/bitsight",
                request,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "create_security_scorecard_intelligence_report",
            description = "Create a new SecurityScorecard intelligence report")
    public ExternalIntelligenceReport createSecurityScorecardIntelligenceReport(
            @Valid CreateSecurityScorecardReportRequest request) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/security-scorecard",
                request,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "get_intelligence_reports_by_vendor",
            description = "Get all intelligence reports for a specific vendor domain")
    public List<ExternalIntelligenceReport> getIntelligenceReportsByVendor(
            @ToolParam(description = "The vendor's primary domain name") String vendorDomain) {
        return apiService.getList(
                EXTERNAL_INTELLIGENCE_API_PATH + "/vendor/" + vendorDomain,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "get_latest_intelligence_report",
            description = "Get the latest intelligence report for a vendor from a specific source")
    public ExternalIntelligenceReport getLatestIntelligenceReport(
            @ToolParam(description = "The vendor's primary domain name") String vendorDomain,
            @ToolParam(
                            description =
                                    "The intelligence report provider (e.g., BITSIGHT or SECURITY_SCORECARD)")
                    IntelligenceProvider source) {
        return apiService.get(
                String.format(
                        "%s/vendor/%s/latest/%s",
                        EXTERNAL_INTELLIGENCE_API_PATH, vendorDomain, source),
                ExternalIntelligenceReport.class);
    }
}
