package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TagsCreateInput(
    @NotNull List<String> tags
) {}
