/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.contexttype.SlimContextTypeView;
import com.visotrust.viso.visomcpserver.model.datatype.SlimDataTypeView;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipUpdateInput(
        @ToolParam(description = "ID of the relationship to update") @NotNull Long id,
        @ToolParam(description = "Name of the relationship/vendor") @NotNull String name,
        @ToolParam(required = false, description = "Homepage URL of the vendor") @Nullable
                String homepage,
        @ToolParam(required = false, description = "Description of the relationship/vendor")
                @Nullable
                String description,
        @ToolParam(
                        required = false,
                        description = "List of business context types for this relationship")
                @Nullable
                List<SlimContextTypeView> contextTypes,
        @ToolParam(
                        required = false,
                        description = "List of data types handled in this relationship")
                @Nullable
                List<SlimDataTypeView> dataTypes,
        @ToolParam(required = false, description = "First name of the business owner") @Nullable
                String businessOwnerFirstName,
        @ToolParam(required = false, description = "Last name of the business owner") @Nullable
                String businessOwnerLastName,
        @ToolParam(required = false, description = "Email address of the business owner") @Nullable
                String businessOwnerEmail,
        @ToolParam(required = false, description = "List of tags to categorize this relationship")
                @Nullable
                List<String> tags) {}
