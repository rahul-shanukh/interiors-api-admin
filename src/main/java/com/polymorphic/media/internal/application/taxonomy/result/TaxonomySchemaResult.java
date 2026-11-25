package com.polymorphic.media.internal.application.taxonomy.result;

import java.util.Set;

public record TaxonomySchemaResult(
        String type,
        String description,
        Set<String> values
) {}

