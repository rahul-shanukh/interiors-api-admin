package com.polymorphic.media.internal.presentation.rest.exception;

import java.util.List;

import com.polymorphic.media.internal.presentation.rest.common.dto.ApiError;
import com.polymorphic.media.internal.presentation.rest.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.accept.InvalidApiVersionException;
import org.springframework.web.accept.MissingApiVersionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(
        basePackages = "com.polymorphic.media.internal.presentation.rest"
)
public class GlobalApiExceptionHandler {

    @ExceptionHandler(InvalidApiVersionException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidApiVersion(
            InvalidApiVersionException exception
    ) {
        return buildApiVersionError(
                "INVALID_API_VERSION",
                "The requested API version is invalid or unsupported.",
                exception.getStatusCode()
        );
    }

    @ExceptionHandler(MissingApiVersionException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingApiVersion(
            MissingApiVersionException exception
    ) {
        return buildApiVersionError(
                "MISSING_API_VERSION",
                "API version is required.",
                exception.getStatusCode()
        );
    }

    private ResponseEntity<ApiResponse<Void>> buildApiVersionError(
            String errorCode,
            String errorDetails,
            org.springframework.http.HttpStatusCode statusCode
    ) {
        ApiError error = new ApiError(errorCode, errorDetails, List.of());

        ApiResponse<Void> response = ApiResponse.failure(
                "API version error",
                error
        );

        return ResponseEntity.status(statusCode).body(response);
    }
}
