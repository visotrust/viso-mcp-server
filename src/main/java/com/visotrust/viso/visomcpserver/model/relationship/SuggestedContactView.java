/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;

public record SuggestedContactView(
        @Nullable String email, @Nullable String firstName, @Nullable String lastName) {}
