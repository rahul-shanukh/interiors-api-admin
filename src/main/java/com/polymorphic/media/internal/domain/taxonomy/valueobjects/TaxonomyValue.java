package com.polymorphic.media.internal.domain.taxonomy.valueobjects;

import java.util.Objects;

public record TaxonomyValue(String value) implements Comparable<TaxonomyValue> {
    public TaxonomyValue {
        Objects.requireNonNull(value, "Taxonomy value cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException("Taxonomy value cannot be blank");
        }
        value = value.trim().toLowerCase();
    }

    @Override
    public int compareTo(TaxonomyValue o) {
        return value.compareTo(o.value());
    }
}