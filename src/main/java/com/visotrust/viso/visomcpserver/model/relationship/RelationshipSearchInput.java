/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.assessment.AssessmentStatus;
import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import jakarta.annotation.Nullable;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * Body of {@code POST /api/v1/relationships/search}. Every field is optional; filters combine with
 * AND, and values within one list filter combine with OR.
 */
public record RelationshipSearchInput(
        @ToolParam(
                        required = false,
                        description =
                                "Vendor domain names, e.g. example.com. Matches relationships whose vendor has any of these domains.")
                @Nullable
                List<String> domains,
        @ToolParam(
                        required = false,
                        description =
                                "Exact relationship name as shown in VISO TRUST. This is an exact match, not a partial or fuzzy search.")
                @Nullable
                String name,
        @ToolParam(required = false, description = "Exact external id of the relationship")
                @Nullable
                String externalId,
        @ToolParam(
                        required = false,
                        description =
                                "Tag names, exactly as they appear in VISO TRUST. Matches relationships carrying any of these tags.")
                @Nullable
                List<String> tags,
        @ToolParam(
                        required = false,
                        description =
                                "Relationship statuses to include. Leave unset to include all statuses.")
                @Nullable
                List<RelationshipStatus> statuses,
        @ToolParam(
                        required = false,
                        description = "Statuses of the relationship's latest assessment to include")
                @Nullable
                List<AssessmentStatus> assessmentStatuses,
        @ToolParam(required = false, description = "Risk acceptance statuses to include") @Nullable
                List<RiskAcceptanceStatus> riskAcceptanceStatuses,
        @ToolParam(required = false, description = "Residual risk levels to include") @Nullable
                List<RiskLevel> residualRiskLevels,
        @ToolParam(required = false, description = "Inherent risk levels to include") @Nullable
                List<RiskLevel> inherentRiskLevels,
        @ToolParam(required = false, description = "Minimum relationship score (inclusive)")
                @Nullable
                Float minScore,
        @ToolParam(required = false, description = "Maximum relationship score (inclusive)")
                @Nullable
                Float maxScore,
        @ToolParam(
                        required = false,
                        description = "Business unit ids (use get_all_business_units to find them)")
                @Nullable
                List<Long> businessUnitIds,
        @ToolParam(
                        required = false,
                        description =
                                "Business owner user ids (use get_all_users or get_user_by_email to find them)")
                @Nullable
                List<Long> businessOwnerIds,
        @ToolParam(
                        required = false,
                        description =
                                "Ids of the users who created the relationship (use get_all_users or get_user_by_email to find them)")
                @Nullable
                List<Long> creatorIds,
        @ToolParam(
                        required = false,
                        description = "Only relationships created after this ISO-8601 date-time")
                @Nullable
                OffsetDateTime createdAfter,
        @ToolParam(
                        required = false,
                        description = "Only relationships created before this ISO-8601 date-time")
                @Nullable
                OffsetDateTime createdBefore,
        @ToolParam(
                        required = false,
                        description =
                                "Only relationships whose recertification date is after this ISO-8601 date-time")
                @Nullable
                OffsetDateTime recertificationAfter,
        @ToolParam(
                        required = false,
                        description =
                                "Only relationships whose recertification date is before this ISO-8601 date-time")
                @Nullable
                OffsetDateTime recertificationBefore,
        @ToolParam(
                        required = false,
                        description = "Only relationships onboarded after this ISO-8601 date-time")
                @Nullable
                OffsetDateTime onboardedAfter,
        @ToolParam(
                        required = false,
                        description = "Only relationships onboarded before this ISO-8601 date-time")
                @Nullable
                OffsetDateTime onboardedBefore,
        @ToolParam(
                        required = false,
                        description =
                                "Only relationships whose latest assessment completed after this ISO-8601 date-time")
                @Nullable
                OffsetDateTime assessmentCompletedAfter,
        @ToolParam(
                        required = false,
                        description =
                                "Only relationships whose latest assessment completed before this ISO-8601 date-time")
                @Nullable
                OffsetDateTime assessmentCompletedBefore) {}
