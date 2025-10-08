/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

public record TagsCreateInput(
        @ToolParam(description = "List of tag names to create for categorizing relationships")
                @NotNull
                List<String> tags) {}
