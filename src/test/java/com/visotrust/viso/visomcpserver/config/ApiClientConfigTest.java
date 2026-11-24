/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.visotrust.viso.visomcpserver.model.assessment.AssessmentStatus;
import com.visotrust.viso.visomcpserver.model.relationship.Relationship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class ApiClientConfigTest {

    private static final String RELATIONSHIP_URL = "https://api.example.test/relationships/1";

    private RestTemplate restTemplate;
    private MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        restTemplate = new ApiClientConfig().restTemplate();
        server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    void readsUnknownEnumValuesAsNullInsteadOfFailingTheResponse() {
        respondWith(
                """
                {
                  "id": 1,
                  "name": "Acme",
                  "status": "ONBOARDED",
                  "isTransitional": false,
                  "latestAssessment": {
                    "aiProcessingOnly": false,
                    "requestedArtifactCollection": false,
                    "status": "STATUS_ADDED_LATER",
                    "statusHistories": [
                      {"status": "COMPLETED"},
                      {"status": "STATUS_ADDED_LATER"}
                    ],
                    "recommendations": [
                      {"recommendationType": "RECOMMENDATION_ADDED_LATER", "description": "d"}
                    ]
                  }
                }
                """);

        Relationship relationship = restTemplate.getForObject(RELATIONSHIP_URL, Relationship.class);

        assertThat(relationship.name()).isEqualTo("Acme");
        assertThat(relationship.latestAssessment().status()).isNull();
        assertThat(relationship.latestAssessment().statusHistories())
                .extracting("status")
                .containsExactly(AssessmentStatus.COMPLETED, null);
        assertThat(relationship.latestAssessment().recommendations())
                .singleElement()
                .satisfies(
                        recommendation -> {
                            assertThat(recommendation.recommendationType()).isNull();
                            assertThat(recommendation.description()).isEqualTo("d");
                        });
    }

    @Test
    void ignoresPropertiesTheModelsDoNotDeclare() {
        respondWith(
                """
                {"id": 1, "name": "Acme", "isTransitional": false, "fieldAddedLater": {"nested": true}}
                """);

        Relationship relationship = restTemplate.getForObject(RELATIONSHIP_URL, Relationship.class);

        assertThat(relationship.id()).isEqualTo(1L);
    }

    private void respondWith(String json) {
        server.expect(requestTo(RELATIONSHIP_URL))
                .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
    }
}
