package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response;

import java.util.Set;

public record TaxonomySchemaResponse(
        String type,
        String description,
        Set<String> values
) {}
