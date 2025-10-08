/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

public enum NoVendorResponseAction {
    CLOSE_COLLECTION_REQUEST,
    NOTIFY_ME;

    public static NoVendorResponseAction getDefault() {
        return NOTIFY_ME;
    }
}
