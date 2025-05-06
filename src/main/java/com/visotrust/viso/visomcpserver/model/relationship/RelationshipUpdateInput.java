package com.visotrust.viso.visomcpserver.model.relationship;

import com.visotrust.viso.visomcpserver.model.contexttype.SlimContextTypeView;
import com.visotrust.viso.visomcpserver.model.datatype.SlimDataTypeView;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RelationshipUpdateInput(
    @NotNull Long id,
    @NotNull String name,
    @Nullable String homepage,
    @Nullable String description,
    @Nullable List<SlimContextTypeView> contextTypes,
    @Nullable List<SlimDataTypeView> dataTypes,
    @Nullable String businessOwnerFirstName,
    @Nullable String businessOwnerLastName,
    @Nullable String businessOwnerEmail,
    @Nullable List<String> tags
) {}
