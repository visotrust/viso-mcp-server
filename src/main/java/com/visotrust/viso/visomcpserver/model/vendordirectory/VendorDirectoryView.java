/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.vendordirectory;

import java.util.Set;

public record VendorDirectoryView(
        Long id,
        String name,
        String homepage,
        String description,
        String faviconUrl,
        Set<String> domains) {}
