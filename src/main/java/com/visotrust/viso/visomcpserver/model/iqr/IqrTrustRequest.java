package com.visotrust.viso.visomcpserver.model.iqr;

import jakarta.validation.constraints.NotNull;

public record IqrTrustRequest(
    @NotNull String question
) {}
