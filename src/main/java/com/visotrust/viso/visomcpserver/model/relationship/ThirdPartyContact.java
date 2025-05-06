package com.visotrust.viso.visomcpserver.model.relationship;

import jakarta.validation.constraints.NotNull;

public record ThirdPartyContact(
    @NotNull String email,
    @NotNull String firstName,
    @NotNull String lastName
) {}
