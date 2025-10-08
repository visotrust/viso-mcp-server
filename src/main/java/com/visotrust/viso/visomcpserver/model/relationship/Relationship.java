/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import com.visotrust.viso.visomcpserver.model.common.VisoUser;
import com.visotrust.viso.visomcpserver.model.contexttype.SlimContextTypeView;
import com.visotrust.viso.visomcpserver.model.datatype.SlimDataTypeView;
import java.time.ZonedDateTime;
import java.util.List;

public record Relationship(
        Long id,
        String name,
        String homepage,
        String description,
        String businessUnit,
        ZonedDateTime createdDate,
        ZonedDateTime updatedDate,
        ZonedDateTime recertificationDate,
        RelationshipStatus status,
        RiskLevel residualRisk,
        RiskLevel inherentRisk,
        boolean isTransitional,
        List<SlimContextTypeView> contextTypes,
        List<SlimDataTypeView> dataTypes,
        VisoUser businessOwner,
        RecertificationType recertificationType,
        List<VisoUser> subscribers,
        PrimaryContact primaryContact,
        List<String> tags,
        String tier,
        List<ComplianceStandard> complianceStandards,
        Assessment latestAssessment) {}
