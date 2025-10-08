/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import org.springframework.ai.tool.annotation.ToolParam;

public record AssessmentCreateRequest(
        @ToolParam(description = "The ID of the relationship for which to create an assessment")
                @NotNull
                Long relationshipId,
        @ToolParam(required = false, description = "Email address of the assessment recipient")
                @Nullable
                @Email
                String recipientEmail,
        @ToolParam(required = false, description = "First name of the assessment recipient")
                @Nullable
                String recipientFirstName,
        @ToolParam(required = false, description = "Last name of the assessment recipient")
                @Nullable
                String recipientLastName,
        @ToolParam(
                        required = false,
                        description =
                                "List of URLs to public documents to be included in the assessment")
                @Nullable
                List<String> publicDocumentUrls,
        @ToolParam(
                        required = false,
                        description = "Type of followup - manual, automatic or concierge")
                @Nullable
                FollowupType followupType,
        @ToolParam(required = false, description = "Risk level threshold that triggers followup")
                @Nullable
                RiskLevel followupRiskThreshold,
        @ToolParam(required = false, description = "Timeline for followup actions (in days)")
                @Nullable
                FollowupTimeline followupTimeline,
        @ToolParam(
                        required = false,
                        description = "Timeline for collecting assessment information (in days)")
                @Nullable
                CollectionTimeline collectionTimeline,
        @ToolParam(required = false, description = "Action to take when vendor does not respond")
                @Nullable
                NoVendorResponseAction noVendorResponseAction,
        @ToolParam(
                        required = false,
                        description = "Whether to only process using AI without human review")
                @Nullable
                Boolean aiProcessingOnly,
        @ToolParam(
                        required = false,
                        description = "Types of audits being requested for this assessment")
                @Nullable
                Set<AuditReportType> requestedAuditTypes) {}
