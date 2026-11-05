/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.relationship;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.visotrust.viso.visomcpserver.model.relationship.Relationship;
import com.visotrust.viso.visomcpserver.model.relationship.RelationshipSearchInput;
import com.visotrust.viso.visomcpserver.model.relationship.RelationshipSearchResult;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RelationshipServiceSearchTest {

    private ApiService apiService;
    private RelationshipService service;

    @BeforeEach
    void setUp() {
        apiService = mock(ApiService.class);
        service = new RelationshipService(apiService);
    }

    @Test
    void searchesByTagsAndReportsPaging() {
        stubResponse(List.of(relationship(), relationship()), "5");
        RelationshipSearchInput input = byTags(List.of("Critical", "PCI"));

        RelationshipSearchResult result =
                service.searchRelationships(input, 1, 2, List.of("createdDate,desc"));

        verify(apiService)
                .postForEntity(
                        eq("/api/v1/relationships/search?page=1&size=2&sort=createdDate,desc"),
                        eq(input),
                        any(ParameterizedTypeReference.class));
        assertThat(result.totalCount()).isEqualTo(5);
        assertThat(result.page()).isEqualTo(1);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.hasMore()).isTrue();
        assertThat(result.relationships()).hasSize(2);
    }

    @Test
    void lastPageHasNoMore() {
        stubResponse(List.of(relationship()), "5");

        RelationshipSearchResult result =
                service.searchRelationships(byTags(List.of("PCI")), 2, 2, null);

        assertThat(result.hasMore()).isFalse();
    }

    @Test
    void defaultsPageAndCapsSize() {
        stubResponse(List.of(), "0");

        service.searchRelationships(byTags(List.of("PCI")), null, 10_000, null);

        verify(apiService)
                .postForEntity(
                        eq("/api/v1/relationships/search?page=0&size=500"),
                        any(),
                        any(ParameterizedTypeReference.class));
    }

    @Test
    void nullRequestSendsEmptyFilters() {
        stubResponse(List.of(), null);
        ArgumentCaptor<RelationshipSearchInput> body =
                ArgumentCaptor.forClass(RelationshipSearchInput.class);

        RelationshipSearchResult result = service.searchRelationships(null, null, null, null);

        verify(apiService)
                .postForEntity(
                        eq("/api/v1/relationships/search?page=0&size=100"),
                        body.capture(),
                        any(ParameterizedTypeReference.class));
        assertThat(body.getValue()).isNotNull();
        assertThat(body.getValue().tags()).isNull();
        assertThat(result.totalCount()).isZero();
        assertThat(result.hasMore()).isFalse();
    }

    @SuppressWarnings("unchecked")
    private void stubResponse(List<Relationship> body, String totalCount) {
        HttpHeaders headers = new HttpHeaders();
        if (totalCount != null) {
            headers.add("X-Total-Count", totalCount);
        }
        when(apiService.postForEntity(any(), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(body, headers, HttpStatus.OK));
    }

    private static Relationship relationship() {
        return mock(Relationship.class);
    }

    private static RelationshipSearchInput byTags(List<String> tags) {
        return new RelationshipSearchInput(
                null, null, null, tags, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null);
    }
}
