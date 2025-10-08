/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipSearchInput(
        @ToolParam(description = "List of domain names to search for relationships") @NotNull
                List<String> domains,
        @ToolParam(description = "Name of the vendor/relationship to search for") @NotNull
                String name) {}
