package com.polymorphic.media.internal.domain.catalog.enums;

public enum DesignStatus {
    DRAFT,         // admin is still filling it in
    UNDER_REVIEW,  // submitted for approval
    APPROVED,      // accepted, not yet visible to customers
    REJECTED,      // sent back for changes
    PUBLISHED,     // visible to customers
    ARCHIVED;      // retired, hidden but kept

    // the only status customers may see
    public boolean isPubliclyVisible() {
        return this == PUBLISHED;
    }
}