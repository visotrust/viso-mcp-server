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

public record RelationshipCreateInput(
        @NotNull String name,
        @NotBlank
                @Pattern(
                        regexp = Constants.URL_REGEX + "|",
                        message = "Invalid url provided for homepage")
                String homepage,
        @Nullable String description,
        @Nullable List<SlimContextTypeView> contextTypes,
        @Nullable List<SlimDataTypeView> dataTypes,
        @Nullable String businessOwnerFirstName,
        @Nullable String businessOwnerLastName,
        @NotBlank String businessOwnerEmail,
        @Nullable List<String> tags,
        @Nullable ThirdPartyContact thirdPartyContact) {}
