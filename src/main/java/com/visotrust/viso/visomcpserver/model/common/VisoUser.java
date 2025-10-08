/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.common;

import jakarta.annotation.Nullable;

public record VisoUser(
        Long id,
        String firstName,
        String lastName,
        String email,
        @Nullable String businessUnit,
        @Nullable UserStatus status) {}
