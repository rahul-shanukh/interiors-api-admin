package com.polymorphic.media.internal.domain.taxonomy.types;

import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyType;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public record TaxonomySchema(
        TaxonomyType type,
        Set<TaxonomyValue> values,
        String description
) {
    public TaxonomySchema {
        values = Collections.unmodifiableSet(
                new TreeSet<>(values)
        );
    }
}