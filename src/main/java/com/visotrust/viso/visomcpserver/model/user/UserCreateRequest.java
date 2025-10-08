/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.user;

import jakarta.validation.constraints.NotNull;
import org.springframework.ai.tool.annotation.ToolParam;

public record UserCreateRequest(
        @ToolParam(description = "Email address of the new user")
                @NotNull(message = "Email must be provided when creating a user")
                String email,
        @ToolParam(description = "First name of the new user")
                @NotNull(message = "First Name must be provided when creating a user")
                String firstName,
        @ToolParam(description = "Last name of the new user")
                @NotNull(message = "Last Name must be provided when creating a user")
                String lastName) {}
