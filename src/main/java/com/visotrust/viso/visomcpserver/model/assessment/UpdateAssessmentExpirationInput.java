/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

import jakarta.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import org.springframework.ai.tool.annotation.ToolParam;

public record UpdateAssessmentExpirationInput(
        @ToolParam(
                        description =
                                "The new expiration date for the assessment (must be in the future)")
                @NotNull
                ZonedDateTime expirationDate) {}
