package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response;

import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomyValueStatus;

import java.time.Instant;

public record TaxonomyValueResponse(
        Long id,
        String value,
        TaxonomyValueStatus status,
        Instant createdAt
) {}
