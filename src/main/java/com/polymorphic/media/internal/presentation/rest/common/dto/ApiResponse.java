
package com.polymorphic.media.internal.presentation.rest.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        ApiError error,
        ApiMetadata metadata,
        String path,
        Instant timestamp
) {

    public ApiResponse {
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");

        if (success && error != null) {
            throw new IllegalArgumentException(
                    "Successful response cannot contain an error"
            );
        }

        if (!success && error == null) {
            throw new IllegalArgumentException(
                    "Failed response must contain error details"
            );
        }
    }

    // Success response without metadata or path
    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {
        return success(message, data, null, null);
    }

    // Success response with metadata
    public static <T> ApiResponse<T> success(
            String message,
            T data,
            ApiMetadata metadata
    ) {
        return success(message, data, metadata, null);
    }

    // Success response with path
    public static <T> ApiResponse<T> success(
            String message,
            T data,
            String path
    ) {
        return success(message, data, null, path);
    }

    // Success response with metadata and path
    public static <T> ApiResponse<T> success(
            String message,
            T data,
            ApiMetadata metadata,
            String path
    ) {
        return new ApiResponse<>(
                true,
                message,
                data,
                null,
                metadata,
                path,
                Instant.now()
        );
    }

    // Failure response without path
    public static <T> ApiResponse<T> failure(
            String message,
            ApiError error
    ) {
        return failure(message, error, null);
    }

    // Failure response with path
    public static <T> ApiResponse<T> failure(
            String message,
            ApiError error,
            String path
    ) {
        return new ApiResponse<>(
                false,
                message,
                null,
                Objects.requireNonNull(error),
                null,
                path,
                Instant.now()
        );
    }
}
