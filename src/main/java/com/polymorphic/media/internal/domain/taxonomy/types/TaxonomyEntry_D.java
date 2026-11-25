package com.polymorphic.media.internal.domain.taxonomy.types;

import java.time.Instant;

public record TaxonomyEntry_D(
        String taxonomyType,   // "MATERIAL"  -> which shelf it sits on
        String taxonomyValue,  // "oak"       -> which allowed word it describes
        String displayName,    // "Oak Wood"  -> what humans see in the dropdown
        boolean active,        // false = hidden from dropdowns, old designs stay valid
        String replacedBy,     // null, or "modern" if this word was merged into another
        Integer sortOrder,     // 1, 2, 3 -> position in the dropdown
        Instant createdAt,
        Instant updatedAt
) {}