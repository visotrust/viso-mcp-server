/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

/**
 * @param version identifies this revision; pass it back when updating to avoid overwriting a newer
 *     summary
 * @param edited whether the summary has been edited since it was generated
 */
public record AssessmentSummary(Long id, String summary, String version, boolean edited) {}
