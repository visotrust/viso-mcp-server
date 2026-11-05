/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.assessment.AssessmentCreateRequest;
import com.visotrust.viso.visomcpserver.model.assessment.AssessmentSummary;
import com.visotrust.viso.visomcpserver.model.assessment.SummaryFormat;
import com.visotrust.viso.visomcpserver.model.assessment.UpdateAssessmentExpirationInput;
import com.visotrust.viso.visomcpserver.model.assessment.UpdateAssessmentFollowupInput;
import com.visotrust.viso.visomcpserver.model.assessment.UpdateAssessmentSummaryInput;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

@Validated
@Service
public class AssessmentService {

    private static final String ASSESSMENTS_API_PATH = "/api/v1/assessments";
    private final ApiService apiService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public AssessmentService(
            ApiService apiService,
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${visotrust.api.base-url}") String baseUrl) {
        this.apiService = apiService;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
    }

    @Tool(
            name = "get_assessment_summary",
            description =
                    "Get the summary for an assessment by its ID, as HTML (default) or Markdown. "
                            + "The returned version can be passed to update_assessment_summary to avoid overwriting a newer edit.")
    public AssessmentSummary getAssessmentSummary(
            @ToolParam(description = "The unique ID of the assessment to get the details for")
                    Long id,
            @ToolParam(
                            required = false,
                            description =
                                    "Format to return the summary in: HTML (default) or MARKDOWN")
                    @Nullable
                    SummaryFormat format) {
        String path = String.format("%s/%d/summary", ASSESSMENTS_API_PATH, id);
        if (format != null) {
            path += "?format=" + format.parameter();
        }
        return apiService.get(path, AssessmentSummary.class);
    }

    @Tool(
            name = "update_assessment_summary",
            description =
                    "Replace the text of a completed assessment's AI-generated summary. The edit is shown in place of "
                            + "the generated summary and is discarded when the summary is regenerated after a new assessment "
                            + "completes. Unsupported markup is removed; images must be embedded as data: URIs. "
                            + "Returns the updated summary in the format it was sent in.")
    public AssessmentSummary updateAssessmentSummary(
            @ToolParam(description = "The unique ID of the assessment") Long id,
            @ToolParam(description = "The edited summary, written in the given format")
                    String summary,
            @ToolParam(
                            required = false,
                            description =
                                    "Format the summary is written in: HTML (default) or MARKDOWN. "
                                            + "Markdown is rendered with the report's own styling.")
                    @Nullable
                    SummaryFormat format,
            @ToolParam(
                            required = false,
                            description =
                                    "The version returned by get_assessment_summary. When given, the update is "
                                            + "rejected if the summary has changed since it was read.")
                    @Nullable
                    String version) {
        UpdateAssessmentSummaryInput body =
                new UpdateAssessmentSummaryInput(
                        summary, format == null ? null : format.parameter(), version);
        return apiService.put(
                String.format("%s/%d/summary", ASSESSMENTS_API_PATH, id),
                body,
                AssessmentSummary.class);
    }

    @Tool(name = "get_assessment", description = "Get an assessment by its ID")
    public Assessment getAssessment(
            @ToolParam(description = "The unique ID of the assessment to get the details for")
                    Long id) {
        return apiService.get(String.format("%s/%d", ASSESSMENTS_API_PATH, id), Assessment.class);
    }

    @Tool(
            name = "create_assessment",
            description = "Start an Assessment for an existing relationship")
    public Assessment createAssessment(@Valid AssessmentCreateRequest request) {
        try {
            // Create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            // Create multipart request body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            // Add the request part
            body.add("request", objectMapper.writeValueAsString(request));

            // Create the request entity
            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            // Send the request
            return restTemplate.postForObject(
                    baseUrl + ASSESSMENTS_API_PATH, requestEntity, Assessment.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create assessment", e);
        }
    }

    @Tool(
            name = "update_assessment_expiration_date",
            description =
                    "Update the assessment expiration date — the deadline by which the vendor must submit their assessment response")
    public String updateAssessmentExpirationDate(
            @ToolParam(description = "The unique ID of the assessment") Long id,
            @Valid UpdateAssessmentExpirationInput request) {
        apiService.put(
                String.format("%s/%d/expiration-date", ASSESSMENTS_API_PATH, id),
                request,
                Void.class);
        return "Assessment expiration date updated.";
    }

    @Tool(
            name = "update_assessment_followup",
            description =
                    "Update the follow-up configuration for an assessment (type, risk threshold, timeline)")
    public String updateAssessmentFollowup(
            @ToolParam(description = "The unique ID of the assessment") Long id,
            @Valid UpdateAssessmentFollowupInput request) {
        apiService.put(
                String.format("%s/%d/followup", ASSESSMENTS_API_PATH, id), request, Void.class);
        return "Assessment follow-up settings updated.";
    }
}
