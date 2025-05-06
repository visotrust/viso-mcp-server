package com.visotrust.viso.visomcpserver.model.assessment;

import java.time.LocalDateTime;

public record AssessmentStatusHistory(
    LocalDateTime date,
    AssessmentStatus status
) {}
