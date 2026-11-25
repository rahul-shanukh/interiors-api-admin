package com.polymorphic.media.internal.domain.catalog.model;

import java.time.Instant;
import java.util.List;

public record CategoryDefinition_D(
        String categoryKey,              // "wardrobe_designs"
        String displayName,              // "Wardrobe Designs"
        String description,
        List<FieldDefinition_D> fields,  // the form recipe
        boolean active,                  // false = hidden from public, visible to admin
        Instant createdAt,
        Instant updatedAt
) {}