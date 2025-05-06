package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.assessment.Assessment;
import com.visotrust.viso.visomcpserver.model.common.RiskLevel;
import com.visotrust.viso.visomcpserver.model.common.VisoUser;
import com.visotrust.viso.visomcpserver.model.contexttype.SlimContextTypeView;
import com.visotrust.viso.visomcpserver.model.datatype.SlimDataTypeView;
import jakarta.annotation.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public record Relationship(
    Long id,
    String name,
    LocalDateTime createdDate,
    @Nullable String homepage,
    @Nullable List<SlimDataTypeView> dataTypes,
    @Nullable List<Assessment> assessments,
    LocalDateTime updatedDate,
    @Nullable List<VisoUser> subscribers,
    RelationshipStatus status,
    @Nullable List<String> tags,
    @Nullable String description,
    @Nullable RecertificationType recertificationType,
    @Nullable PrimaryContact primaryContact,
    @Nullable Boolean isTransitional,
    @Nullable RiskLevel residualRisk,
    @Nullable RiskLevel inherentRisk,
    @Nullable VisoUser businessOwner,
    @Nullable LocalDateTime recertificationDate,
    @Nullable String businessUnit,
    @Nullable List<SlimContextTypeView> contextTypes
) {}
