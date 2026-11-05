/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.detection;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * One page of a relationship's detections.
 *
 * @param riskAssessmentId null when the relationship has not been assessed yet, which is not the
 *     same as having no detections
 * @param artifactsInProgress detections on these artifacts are left out until validation completes
 * @param nextAfterId pass as afterId to read the next page; null on the last page
 */
public record DetectionsResponse(
        Long relationshipId,
        Long assessmentId,
        Long riskAssessmentId,
        ZonedDateTime riskAssessmentDate,
        List<InProgressArtifact> artifactsInProgress,
        int size,
        Long afterId,
        Long nextAfterId,
        List<Detection> detections) {

    public record Detection(
            Long id,
            String detectionType,
            String detectionStatus,
            String text,
            String controlIdsText,
            ZonedDateTime updatedDate,
            Long controlDomainId,
            String controlDomainName,
            String controlDomainType,
            Long controlId,
            String controlName,
            Answer questionnaireAnswer,
            Source source) {}

    public record Source(Long artifactId, String artifactName, String viewerPage) {}

    public record InProgressArtifact(Long artifactId, String artifactName) {}

    public record Answer(
            String answer,
            String aiAnswer,
            boolean answered,
            String answerSource,
            ZonedDateTime answeredDate) {}
}
