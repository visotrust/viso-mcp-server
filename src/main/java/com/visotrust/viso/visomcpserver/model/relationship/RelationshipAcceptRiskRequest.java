/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import jakarta.annotation.Nullable;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipAcceptRiskRequest(
        @ToolParam(required = false, description = "Note explaining the risk acceptance decision")
                @Nullable
                String riskReviewNote,
        @ToolParam(
                        required = false,
                        description =
                                "Inherent risk level to apply as an override (LOW, MEDIUM, HIGH or EXTREME). "
                                        + "Must be given together with residualRiskOverride; omitting both removes any existing override.")
                @Nullable
                RiskLevel inherentRiskOverride,
        @ToolParam(
                        required = false,
                        description =
                                "Residual risk level to apply as an override (LOW, MEDIUM, HIGH or EXTREME). "
                                        + "Must be given together with inherentRiskOverride.")
                @Nullable
                RiskLevel residualRiskOverride) {}
