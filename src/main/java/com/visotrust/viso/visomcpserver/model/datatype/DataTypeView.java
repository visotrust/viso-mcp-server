/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.datatype;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record DataTypeView(@NotNull String name, @Nullable String description) {}
