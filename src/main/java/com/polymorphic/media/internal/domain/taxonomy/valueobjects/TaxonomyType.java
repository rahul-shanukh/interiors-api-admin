package com.polymorphic.media.internal.domain.taxonomy.valueobjects;

import java.util.Objects;

public record TaxonomyType(String value) {
    public TaxonomyType {
        Objects.requireNonNull(value, "Taxonomy type cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Taxonomy type cannot be blank");
        }
        value = value.trim().toUpperCase();
    }
}
