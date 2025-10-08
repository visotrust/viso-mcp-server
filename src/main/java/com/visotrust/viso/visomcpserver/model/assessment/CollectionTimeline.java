/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

public enum CollectionTimeline {
    THIRTY_DAYS(30),
    SIXTY_DAYS(60),
    NINETY_DAYS(90),
    ONE_HUNDRED_TWENTY_DAYS(120);

    private final int days;

    CollectionTimeline(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public static CollectionTimeline getDefault() {
        return THIRTY_DAYS;
    }
}
