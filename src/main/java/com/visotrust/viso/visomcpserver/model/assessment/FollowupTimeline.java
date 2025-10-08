/* Copyright (c) 2025 VISO TRUST */
package com.visotrust.viso.visomcpserver.model.assessment;

public enum FollowupTimeline {
    SEVEN_DAYS(7),
    FOURTEEN_DAYS(14),
    THIRTY_DAYS(30),
    SIXTY_DAYS(60),
    NINETY_DAYS(90);

    private final int days;

    FollowupTimeline(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public static FollowupTimeline getDefault() {
        return SEVEN_DAYS;
    }
}
