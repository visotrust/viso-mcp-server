/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.assessment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.assessment.AssessmentCreateRequest;
import com.visotrust.viso.visomcpserver.model.assessment.AssessmentSummary;
import com.visotrust.viso.visomcpserver.service.ApiService;
import javax.validation.Valid;
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
            description = "Get a the summary for an assessment by its ID")
    public AssessmentSummary getAssessmentSummary(
            @ToolParam(description = "The unique ID of the assessment to get the details for")
                    Long id) {
        return apiService.get(
                String.format("%s/%d/summary", ASSESSMENTS_API_PATH, id), AssessmentSummary.class);
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
}
