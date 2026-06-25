/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.questionnaire;

import java.time.ZonedDateTime;
import java.util.UUID;

public record RelationshipQuestionnaireView(
        UUID id, String name, Boolean enabled, Boolean aiOnly, ZonedDateTime createdDate) {}
