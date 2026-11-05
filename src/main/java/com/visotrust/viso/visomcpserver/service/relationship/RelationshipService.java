/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.relationship;

import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.detection.ControlDomainType;
import com.visotrust.viso.visomcpserver.model.detection.DetectionType;
import com.visotrust.viso.visomcpserver.model.detection.DetectionsResponse;
import com.visotrust.viso.visomcpserver.model.relationship.*;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RelationshipService {

    private static final String RELATIONSHIPS_API_PATH = "/api/v1/relationships";
    private static final String THIRD_PARTY_CONTACT_API_PATH = "/api/v1/third-party-contact";
    private static final String TAGS_API_PATH = "/api/v1/tags";
    private static final String TOTAL_COUNT_HEADER = "X-Total-Count";
    static final int DEFAULT_SEARCH_PAGE_SIZE = 100;
    static final int MAX_SEARCH_PAGE_SIZE = 500;
    private final ApiService apiService;

    public RelationshipService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_relationships",
            description =
                    "Get a list of all relationships and their assessment details. Returns information about third-party vendors including their assessment status, risk levels, and contact details.")
    public List<Relationship> getAllRelationships() {
        return apiService.getList(RELATIONSHIPS_API_PATH, Relationship.class);
    }

    @Tool(
            name = "get_relationship_by_id",
            description =
                    "Get a specific relationship and its assessment details by ID. Returns detailed information about a third-party vendor including assessment status, risk levels, and contact details.")
    public Relationship getRelationshipById(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.get(RELATIONSHIPS_API_PATH + "/" + id, Relationship.class);
    }

    @Tool(
            name = "get_relationship_assessment_history",
            description =
                    "Get a list of all of the assessments associated with a relationship by its ID.")
    public List<Assessment> getRelationshipAssessmentHistory(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.getList(
                String.format("%s/%d/assessments", "/api/v1/relationship", id), Assessment.class);
    }

    @Tool(
            name = "create_relationship",
            description =
                    "Create a new relationship with a third-party vendor. Requires vendor name, and a Business Owner email, supports optional information such as homepage, business context, and data types.")
    public Relationship createRelationship(RelationshipCreateInput request) {
        return apiService.post(RELATIONSHIPS_API_PATH, request, Relationship.class);
    }

    @Tool(
            name = "create_relationship_by_domain",
            description =
                    "Initialize a new vendor relationship by providing only the essential domain name and vendor name. This simplified creation method requires minimal information to establish a basic vendor record.")
    public Relationship createRelationshipByDomain(CreateRelationshipByDomainRequest request) {
        return apiService.post(
                String.format("%s/domain", RELATIONSHIPS_API_PATH), request, Relationship.class);
    }

    @Tool(
            name = "update_relationship",
            description =
                    "Update an existing relationship with a third-party vendor. Can modify details such as vendor name, homepage, business context, data types, business owner, and tags.")
    public Relationship updateRelationship(RelationshipUpdateInput request) {
        return apiService.put(RELATIONSHIPS_API_PATH, request, Relationship.class);
    }

    @Tool(
            name = "partially_update_relationship",
            description =
                    "Partially update an existing relationship with a third-party vendor. Only updates the fields that are provided in the request, leaving other fields unchanged.")
    public Relationship partiallyUpdateRelationship(RelationshipUpdateInput request) {
        return apiService.patch(RELATIONSHIPS_API_PATH, request, Relationship.class);
    }

    @Tool(
            name = "search_relationships",
            description =
                    "Search the organization's relationships by filter criteria and get back one page of matches. "
                            + "Every filter is optional; filters combine with AND, and values within one list filter combine with OR. "
                            + "Filter by tags, vendor domains, exact relationship name, external id, relationship status, "
                            + "assessment status, risk acceptance status, inherent or residual risk level, score range, "
                            + "business unit, business owner or creator ids, and created, onboarded, recertification or assessment-completed date ranges. "
                            + "Prefer this over get_all_relationships whenever the question is about which relationships match. "
                            + "Results are paged: totalCount is the full number of matches; when hasMore is true, request the next page "
                            + "or narrow the filters rather than presenting the list as complete.")
    public RelationshipSearchResult searchRelationships(
            @ToolParam(required = false, description = "Search filters; omit to match everything")
                    @Nullable
                    RelationshipSearchInput request,
            @ToolParam(required = false, description = "Zero-based page number (default 0)")
                    @Nullable
                    Integer page,
            @ToolParam(
                            required = false,
                            description =
                                    "Number of relationships per page (default "
                                            + DEFAULT_SEARCH_PAGE_SIZE
                                            + ", max "
                                            + MAX_SEARCH_PAGE_SIZE
                                            + ")")
                    @Nullable
                    Integer size,
            @ToolParam(
                            required = false,
                            description =
                                    "Sort orders in the format property,asc|desc, e.g. createdDate,desc. "
                                            + "Supported properties: name, externalId, score, createdDate, onboardedDate, recertificationDate.")
                    @Nullable
                    List<String> sort) {
        int pageNumber = page == null || page < 0 ? 0 : page;
        int pageSize =
                size == null || size < 1
                        ? DEFAULT_SEARCH_PAGE_SIZE
                        : Math.min(size, MAX_SEARCH_PAGE_SIZE);
        UriComponentsBuilder uri =
                UriComponentsBuilder.fromPath(RELATIONSHIPS_API_PATH + "/search")
                        .queryParam("page", pageNumber)
                        .queryParam("size", pageSize);
        if (sort != null) {
            sort.stream().filter(StringUtils::hasText).forEach(s -> uri.queryParam("sort", s));
        }
        ResponseEntity<List<Relationship>> response =
                apiService.postForEntity(
                        uri.build().toUriString(),
                        request == null ? emptySearchInput() : request,
                        new ParameterizedTypeReference<>() {});
        List<Relationship> relationships =
                response.getBody() == null ? List.of() : response.getBody();
        long offset = (long) pageNumber * pageSize;
        long totalCount = totalCount(response.getHeaders(), offset + relationships.size());
        return new RelationshipSearchResult(
                totalCount,
                pageNumber,
                pageSize,
                offset + relationships.size() < totalCount,
                relationships);
    }

    private static RelationshipSearchInput emptySearchInput() {
        return new RelationshipSearchInput(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null);
    }

    /** Total matches from the X-Total-Count header, or {@code fallback} when it is absent. */
    static long totalCount(HttpHeaders headers, long fallback) {
        String value = headers.getFirst(TOTAL_COUNT_HEADER);
        if (!StringUtils.hasText(value)) {
            return fallback;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    @Tool(
            name = "create_tags",
            description =
                    "Create new tags that can be used to categorize and filter relationships. Returns a list of all tags including the newly created ones.")
    public List<String> createTags(TagsCreateInput request) {
        return apiService.post(TAGS_API_PATH, request, new ParameterizedTypeReference<>() {});
    }

    @Tool(
            name = "update_third_party_contact",
            description =
                    "Update the contact details for a third-party vendor. This allows you to specify or change the primary contact person at the vendor organization who will receive assessment requests.")
    public Relationship updateThirdPartyContact(ThirdPartyContactUpdateRequest request) {
        return apiService.put(THIRD_PARTY_CONTACT_API_PATH, request, Relationship.class);
    }

    @Tool(
            name = "onboard_relationship",
            description =
                    "Onboard a relationship, optionally providing an approval summary and lifecycle management settings.")
    public Relationship onboardRelationship(
            @ToolParam(description = "The id of the relationship") Long id,
            @ToolParam(required = false, description = "Optional onboarding details") @Nullable
                    RelationshipOnboardRequest request) {
        return apiService.put(
                String.format("%s/%d/onboard", RELATIONSHIPS_API_PATH, id),
                request,
                Relationship.class);
    }

    @Tool(name = "offboard_relationship", description = "Offboard a relationship.")
    public Relationship offboardRelationship(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.put(
                String.format("%s/%d/offboard", RELATIONSHIPS_API_PATH, id),
                null,
                Relationship.class);
    }

    @Tool(
            name = "accept_relationship_risk",
            description =
                    "Accept the risk of a relationship as the current user, optionally with a review note and an "
                            + "inherent/residual risk override. Risk cannot be accepted while the relationship's latest "
                            + "completed assessment has an active remediation request.")
    public Relationship acceptRelationshipRisk(
            @ToolParam(description = "The id of the relationship") Long id,
            @ToolParam(required = false, description = "Optional note and risk override") @Nullable
                    RelationshipAcceptRiskRequest request) {
        return apiService.put(
                String.format("%s/%d/accept-risk", RELATIONSHIPS_API_PATH, id),
                request,
                Relationship.class);
    }

    @Tool(
            name = "get_relationship_detections",
            description =
                    "Get the detections (exceptions, CUECs, subservice organizations, subprocessors, shared "
                            + "responsibility statements and, if requested, normal control evidence) found in a relationship's "
                            + "evidence, one entry per detection in id order. Results are paged: pass nextAfterId as afterId "
                            + "until it is null; if riskAssessmentId changes between pages the evidence changed, so start again. "
                            + "riskAssessmentId is null when the relationship has not been assessed yet, which is not the same "
                            + "as having no detections. Detections on artifactsInProgress are left out until validation completes.")
    public DetectionsResponse getRelationshipDetections(
            @ToolParam(description = "The id of the relationship") Long id,
            @ToolParam(
                            required = false,
                            description =
                                    "Only these detection types. Defaults to every type except NORMAL, which only "
                                            + "evidences that a control is in place.")
                    @Nullable
                    List<DetectionType> detectionTypes,
            @ToolParam(
                            required = false,
                            description =
                                    "Only detections in these kinds of control domain. Defaults to all.")
                    @Nullable
                    List<ControlDomainType> controlDomainTypes,
            @ToolParam(
                            required = false,
                            description =
                                    "A completed assessment of this relationship, to see what it saw, or the "
                                            + "relationship's latest assessment while in progress. Omit for the current picture.")
                    @Nullable
                    Long assessmentId,
            @ToolParam(
                            required = false,
                            description =
                                    "Also return the answers behind supplemental questionnaire detections (default false)")
                    @Nullable
                    Boolean includeQuestionnaireAnswers,
            @ToolParam(
                            required = false,
                            description =
                                    "The previous page's nextAfterId. Omit for the first page.")
                    @Nullable
                    Long afterId,
            @ToolParam(
                            required = false,
                            description = "Detections per page, 1 to 500 (default 100)")
                    @Nullable
                    Integer size) {
        UriComponentsBuilder uri =
                UriComponentsBuilder.fromPath(
                        String.format("%s/%d/detections", RELATIONSHIPS_API_PATH, id));
        if (detectionTypes != null) {
            detectionTypes.forEach(t -> uri.queryParam("detectionType", t.name()));
        }
        if (controlDomainTypes != null) {
            controlDomainTypes.forEach(t -> uri.queryParam("controlDomainType", t.name()));
        }
        if (assessmentId != null) {
            uri.queryParam("assessmentId", assessmentId);
        }
        if (Boolean.TRUE.equals(includeQuestionnaireAnswers)) {
            uri.queryParam("includeQuestionnaireAnswers", true);
        }
        if (afterId != null) {
            uri.queryParam("afterId", afterId);
        }
        if (size != null) {
            uri.queryParam("size", size);
        }
        return apiService.get(uri.build().toUriString(), DetectionsResponse.class);
    }

    @Tool(name = "archive_relationship", description = "Archive a relationship.")
    public Relationship archiveRelationship(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.put(
                String.format("%s/%d/archive", RELATIONSHIPS_API_PATH, id),
                null,
                Relationship.class);
    }
}
