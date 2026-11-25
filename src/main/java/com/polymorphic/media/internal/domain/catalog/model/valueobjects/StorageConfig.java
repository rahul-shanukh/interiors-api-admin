package com.polymorphic.media.internal.domain.catalog.model.valueobjects;

import java.util.Map;
import java.util.Objects;

public record StorageConfig(
        String bucketName,                // "processed-assets-prod"
        String objectPath,                // "images/a3f7c2d1-.../"
        Map<String, String> variants,     // {"hero":"hero.webp","medium":"medium.webp"}
        String mimeType,                  // "image/webp"
        long sizeInBytes,
        Integer width,                    // pixels
        Integer height,
        String fileHash                   // SHA-256, to detect duplicate uploads
) {
    public StorageConfig {
        Objects.requireNonNull(bucketName, "bucketName must not be null");
        Objects.requireNonNull(objectPath, "objectPath must not be null");
        Objects.requireNonNull(mimeType, "mimeType must not be null");
        if (sizeInBytes < 0) {
            throw new IllegalArgumentException("sizeInBytes must not be negative");
        }
        variants = (variants == null) ? Map.of() : Map.copyOf(variants);
    }
}