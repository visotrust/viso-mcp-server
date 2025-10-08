/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.Constants;
import com.visotrust.viso.visomcpserver.model.contexttype.SlimContextTypeView;
import com.visotrust.viso.visomcpserver.model.datatype.SlimDataTypeView;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.ai.tool.annotation.ToolParam;

public record RelationshipCreateInput(
        @ToolParam(description = "Name of the relationship/vendor") @NotNull String name,
        @ToolParam(description = "Homepage URL of the vendor")
                @NotBlank
                @Pattern(
                        regexp = Constants.URL_REGEX + "|",
                        message = "Invalid url provided for homepage")
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
        @ToolParam(description = "Email address of the business owner") @NotBlank
                String businessOwnerEmail,
        @ToolParam(required = false, description = "List of tags to categorize this relationship")
                @Nullable
                List<String> tags,
        @ToolParam(
                        required = false,
                        description = "Contact details of the third party vendor representative")
                @Nullable
                ThirdPartyContact thirdPartyContact) {}
