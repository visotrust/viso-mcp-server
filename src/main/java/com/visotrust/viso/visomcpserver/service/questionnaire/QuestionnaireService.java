/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.service.questionnaire;

import com.visotrust.viso.visomcpserver.model.questionnaire.QuestionnaireView;
import com.visotrust.viso.visomcpserver.model.questionnaire.RelationshipQuestionnaireView;
import com.visotrust.viso.visomcpserver.model.questionnaire.RelationshipQuestionnairesUpdateInput;
import com.visotrust.viso.visomcpserver.service.ApiService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class QuestionnaireService {

    private static final String QUESTIONNAIRES_API_PATH = "/api/v1/questionnaires";
    private static final String RELATIONSHIPS_API_PATH = "/api/v1/relationships";
    private final ApiService apiService;

    public QuestionnaireService(ApiService apiService) {
        this.apiService = apiService;
    }

    @Tool(
            name = "get_all_questionnaires",
            description =
                    "Get all questionnaires available to your organization, with their organization-level enabled state. Use the returned IDs with get_relationship_questionnaires, update_relationship_questionnaires, and the create_assessment 'questionnaires' field.")
    public List<QuestionnaireView> getAllQuestionnaires() {
        return apiService.getList(QUESTIONNAIRES_API_PATH, QuestionnaireView.class);
    }

    @Tool(
            name = "get_relationship_questionnaires",
            description =
                    "Get all questionnaires available for a relationship, with the effective enabled state for that relationship (relationship-level overrides fall back to the organization-level default).")
    public List<RelationshipQuestionnaireView> getRelationshipQuestionnaires(
            @ToolParam(description = "The id of the relationship") Long id) {
        return apiService.getList(
                String.format("%s/%d/questionnaires", RELATIONSHIPS_API_PATH, id),
                RelationshipQuestionnaireView.class);
    }

    @Tool(
            name = "update_relationship_questionnaires",
            description =
                    "Set which questionnaires are enabled for a relationship. Replaces the relationship's enabled-questionnaire list; any active organization questionnaire not included is disabled for the relationship.")
    public List<RelationshipQuestionnaireView> updateRelationshipQuestionnaires(
            @ToolParam(description = "The id of the relationship") Long id,
            @Valid RelationshipQuestionnairesUpdateInput request) {
        return apiService.put(
                String.format("%s/%d/questionnaires", RELATIONSHIPS_API_PATH, id),
                request,
                new ParameterizedTypeReference<>() {});
    }
}
