/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.ai.tool.annotation.ToolParam;

public record CreateRelationshipByDomainRequest(
        @ToolParam(description = "Domain of the vendor (ex: visotrust.com)") @NotBlank
                String domain,
        @ToolParam(description = "Name of the vendor") @NotBlank String vendorName,
        @ToolParam(required = false, description = "Product offered by the vendor (optional)")
                @Nullable
                String product,
        @ToolParam(
                        required = false,
                        description = "Description of the vendor relationship (optional)")
                @Nullable
                String description) {}
