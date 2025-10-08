/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import java.time.ZonedDateTime;

public record AssessmentRecommendation(
        ZonedDateTime createdDate, RecommendationType recommendationType, String description) {}
