/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.assessment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.visotrust.viso.visomcpserver.config.ApiClientConfig;
import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.assessment.AssessmentCreateRequest;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class AssessmentServiceCreateTest {

    private static final String BASE_URL = "https://api.example.test";
    private static final String CALLER_JWT = "caller-jwt";

    private MockRestServiceServer server;
    private AssessmentService service;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new ApiClientConfig().restTemplate();
        server = MockRestServiceServer.bindTo(restTemplate).build();
        service = new AssessmentService(new ApiService(restTemplate, BASE_URL, null));
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken("caller", CALLER_JWT, List.of()));
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void sendsTheRequestAsAJsonPartWithTheCallersToken() {
        server.expect(requestTo(BASE_URL + "/api/v1/assessments"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header("Authorization", "Bearer " + CALLER_JWT))
                .andExpect(content().contentTypeCompatibleWith(MediaType.MULTIPART_FORM_DATA))
                .andExpect(
                        request ->
                                assertThat(((MockClientHttpRequest) request).getBodyAsString())
                                        .contains("name=\"request\"")
                                        .contains("Content-Type: application/json")
                                        .contains("\"relationshipId\":42")
                                        .contains("\"recipientEmail\":\"vendor@example.com\""))
                .andRespond(withSuccess("{\"id\": 7}", MediaType.APPLICATION_JSON));

        Assessment assessment = service.createAssessment(request());

        server.verify();
        assertThat(assessment.id()).isEqualTo(7L);
    }

    private static AssessmentCreateRequest request() {
        return new AssessmentCreateRequest(
                42L,
                "vendor@example.com",
                "Vee",
                "Endor",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }
}
