package com.visotrust.viso.visomcpserver.model.contexttype;

import jakarta.validation.constraints.NotNull;

public record SlimContextTypeView(
    @NotNull String name
) {}
