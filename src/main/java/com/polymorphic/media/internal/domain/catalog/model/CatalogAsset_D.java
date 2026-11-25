package com.polymorphic.media.internal.domain.catalog.model;

import com.polymorphic.media.internal.domain.catalog.enums.DesignStatus;
import com.polymorphic.media.internal.domain.catalog.model.valueobjects.StorageConfig;

import java.time.Instant;
import java.util.Map;

public record CatalogAsset_D(
        String id,
        String categoryKey,              // links to CategoryDefinition_D
        String title,
        String  description,
        Map<String, Object> values,      // {"door_type":"hinged","width":6,"features":["mirror"]}
        DesignStatus status,
        StorageConfig media,
        Instant createdAt,
        Instant updatedAt
) {}