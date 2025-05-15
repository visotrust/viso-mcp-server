/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.contexttype;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record ContextTypeView(@NotNull String name, @Nullable String description) {}
