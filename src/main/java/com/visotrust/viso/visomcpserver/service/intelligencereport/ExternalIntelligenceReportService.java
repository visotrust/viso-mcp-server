/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.intelligencereport;

import com.visotrust.viso.visomcpserver.model.intelligencereport.BulkIntelligenceReportResult;
import com.visotrust.viso.visomcpserver.model.intelligencereport.CreateBitsightReportRequest;
import com.visotrust.viso.visomcpserver.model.intelligencereport.CreateRecordedFutureReportRequest;
import com.visotrust.viso.visomcpserver.model.intelligencereport.CreateSecurityScorecardReportRequest;
import com.visotrust.viso.visomcpserver.model.intelligencereport.ExternalIntelligenceReport;
import com.visotrust.viso.visomcpserver.model.intelligencereport.IntelligenceProvider;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
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
            name = "create_recorded_future_intelligence_report",
            description = "Create a new Recorded Future intelligence report")
    public ExternalIntelligenceReport createRecordedFutureIntelligenceReport(
            @Valid CreateRecordedFutureReportRequest request) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/recorded-future",
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

    @Tool(
            name = "get_intelligence_report",
            description = "Get an external intelligence report from any provider by its id")
    public ExternalIntelligenceReport getIntelligenceReport(
            @ToolParam(description = "The id of the intelligence report") UUID id) {
        return apiService.get(
                EXTERNAL_INTELLIGENCE_API_PATH + "/" + id, ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "update_bitsight_intelligence_report",
            description =
                    "Replace an existing BitSight intelligence report. Every field is replaced with the request; "
                            + "optional fields left out are cleared. Changing vendorDomain moves the report to that vendor.")
    public ExternalIntelligenceReport updateBitsightIntelligenceReport(
            @ToolParam(description = "The id of the BitSight intelligence report") UUID id,
            @Valid CreateBitsightReportRequest request) {
        return apiService.put(
                EXTERNAL_INTELLIGENCE_API_PATH + "/bitsight/" + id,
                request,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "update_security_scorecard_intelligence_report",
            description =
                    "Replace an existing SecurityScorecard intelligence report. Every field is replaced with the "
                            + "request; optional fields left out are cleared. Changing vendorDomain moves the report to that vendor.")
    public ExternalIntelligenceReport updateSecurityScorecardIntelligenceReport(
            @ToolParam(description = "The id of the SecurityScorecard intelligence report") UUID id,
            @Valid CreateSecurityScorecardReportRequest request) {
        return apiService.put(
                EXTERNAL_INTELLIGENCE_API_PATH + "/security-scorecard/" + id,
                request,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "update_recorded_future_intelligence_report",
            description =
                    "Replace an existing Recorded Future intelligence report. Every field is replaced with the "
                            + "request; optional fields left out are cleared. Changing vendorDomain moves the report to that vendor.")
    public ExternalIntelligenceReport updateRecordedFutureIntelligenceReport(
            @ToolParam(description = "The id of the Recorded Future intelligence report") UUID id,
            @Valid CreateRecordedFutureReportRequest request) {
        return apiService.put(
                EXTERNAL_INTELLIGENCE_API_PATH + "/recorded-future/" + id,
                request,
                ExternalIntelligenceReport.class);
    }

    @Tool(
            name = "delete_intelligence_report",
            description =
                    "Permanently delete an external intelligence report from any provider. This cannot be undone.")
    public String deleteIntelligenceReport(
            @ToolParam(description = "The id of the intelligence report") UUID id) {
        apiService.delete(EXTERNAL_INTELLIGENCE_API_PATH + "/" + id);
        return "Deleted intelligence report " + id;
    }

    @Tool(
            name = "bulk_create_bitsight_intelligence_reports",
            description =
                    "Create up to 1000 BitSight intelligence reports in one call. Large batches may be created in the "
                            + "background: created lists the ids made immediately, "
                            + "backgroundedCount the reports still queued, and totalFailureCount the reports that failed.")
    public BulkIntelligenceReportResult bulkCreateBitsightIntelligenceReports(
            @ToolParam(description = "The reports to create (1 to 1000)") @Valid
                    List<CreateBitsightReportRequest> reports) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/bitsight/bulk",
                reports,
                new ParameterizedTypeReference<BulkIntelligenceReportResult>() {});
    }

    @Tool(
            name = "bulk_create_security_scorecard_intelligence_reports",
            description =
                    "Create up to 1000 SecurityScorecard intelligence reports in one call. Large batches may be created "
                            + "in the background: created lists the ids made immediately, "
                            + "backgroundedCount the reports still queued, and totalFailureCount the reports that failed.")
    public BulkIntelligenceReportResult bulkCreateSecurityScorecardIntelligenceReports(
            @ToolParam(description = "The reports to create (1 to 1000)") @Valid
                    List<CreateSecurityScorecardReportRequest> reports) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/security-scorecard/bulk",
                reports,
                new ParameterizedTypeReference<BulkIntelligenceReportResult>() {});
    }

    @Tool(
            name = "bulk_create_recorded_future_intelligence_reports",
            description =
                    "Create up to 1000 Recorded Future intelligence reports in one call. Large batches may be created "
                            + "in the background: created lists the ids made immediately, "
                            + "backgroundedCount the reports still queued, and totalFailureCount the reports that failed.")
    public BulkIntelligenceReportResult bulkCreateRecordedFutureIntelligenceReports(
            @ToolParam(description = "The reports to create (1 to 1000)") @Valid
                    List<CreateRecordedFutureReportRequest> reports) {
        return apiService.post(
                EXTERNAL_INTELLIGENCE_API_PATH + "/recorded-future/bulk",
                reports,
                new ParameterizedTypeReference<BulkIntelligenceReportResult>() {});
    }
}
