package com.visotrust.viso.visomcpserver.model.common;

import jakarta.annotation.Nullable;

public record VisoUser(
    String email,
    String firstName,
    String lastName,
    @Nullable String businessUnit
) {}
