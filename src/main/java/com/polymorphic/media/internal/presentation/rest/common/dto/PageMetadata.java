
package com.polymorphic.media.internal.presentation.rest.common.dto;

public record PageMetadata(
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {}
