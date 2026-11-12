/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

public enum SummaryFormat {
    HTML,
    MARKDOWN;

    public String parameter() {
        return name().toLowerCase();
    }
}
