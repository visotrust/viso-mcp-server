/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.questionnaire;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipQuestionnairesUpdateInput(
        @ToolParam(
                        description =
                                "IDs of the questionnaires to enable for this relationship. Any active organization questionnaire not in this set is disabled for the relationship. Pass an empty set to disable all.")
                @NotNull
                Set<UUID> enabledQuestionnaireIds) {}
