/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

/** Body of PUT /api/v1/assessments/{id}/summary. */
public record UpdateAssessmentSummaryInput(String summary, String format, String version) {}
