/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.ai.tool.annotation.ToolParam;

public record UpdateAssessmentFollowupInput(
        @ToolParam(description = "Follow-up type: MANUAL, AUTOMATIC, or CONCIERGE") @NotNull
                FollowupType followupType,
        @ToolParam(
                        required = false,
                        description =
                                "Risk threshold at or above which a follow-up assessment should be triggered")
                @Nullable
                RiskLevel followupRiskThreshold,
        @ToolParam(
                        required = false,
                        description =
                                "Follow-up timeline (SEVEN_DAYS, FOURTEEN_DAYS, THIRTY_DAYS, SIXTY_DAYS, NINETY_DAYS)")
                @Nullable
                FollowupTimeline followupTimeline) {}
