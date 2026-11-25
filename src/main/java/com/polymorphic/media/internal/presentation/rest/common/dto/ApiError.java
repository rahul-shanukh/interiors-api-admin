
package com.polymorphic.media.internal.presentation.rest.common.dto;

import java.util.List;

public record ApiError(
        String code,
        String details,
        List<FieldError> fieldErrors
) {

    public ApiError {
        fieldErrors = fieldErrors == null
                ? List.of()
                : List.copyOf(fieldErrors);
    }

    public record FieldError(
            String field,
            String message
    ) {}
}
