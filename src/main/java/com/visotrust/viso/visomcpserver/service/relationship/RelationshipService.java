/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.relationship;

import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.relationship.*;
import com.visotrust.viso.visomcpserver.service.ApiService;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    private static final String RELATIONSHIPS_API_PATH = "/api/v1/relationships";
    private static final String THIRD_PARTY_CONTACT_API_PATH = "/api/v1/third-party-contact";
    private static final String TAGS_API_PATH = "/api/v1/tags";
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
            name = "get_suggested_contacts",
            description =
                    "Get a list of suggested contacts for a specific relationship. These are potential contacts at the vendor organization that the system has identified based on previous interactions or public information.")
    public List<SuggestedContactView> getSuggestedContacts(
            @ToolParam(description = "The id of the relationship") Long relationshipId) {
        return apiService.getList(
                RELATIONSHIPS_API_PATH + "/" + relationshipId + "/suggested-contacts",
                SuggestedContactView.class);
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
                    "Search for relationships by domain name or vendor name. Returns a list of matching relationships with their assessment details.")
    public List<Relationship> searchRelationships(RelationshipSearchInput request) {
        return apiService.post(
                RELATIONSHIPS_API_PATH + "/search", request, new ParameterizedTypeReference<>() {});
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
}
