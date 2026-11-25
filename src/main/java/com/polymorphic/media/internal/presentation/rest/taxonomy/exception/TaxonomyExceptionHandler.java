package com.polymorphic.media.internal.presentation.rest.taxonomy.exception;

import java.util.List;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.polymorphic.media.internal.application.taxonomy.exception.DuplicateTaxonomyException;
import com.polymorphic.media.internal.application.taxonomy.exception.TaxonomyNotFoundException;
import com.polymorphic.media.internal.presentation.rest.common.dto.ApiError;
import com.polymorphic.media.internal.presentation.rest.common.dto.ApiResponse;

@RestControllerAdvice(
        basePackages = "com.polymorphic.media.internal.presentation.rest.taxonomy"
)
public class TaxonomyExceptionHandler {

    @ExceptionHandler(TaxonomyNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(
            TaxonomyNotFoundException exception
    ) {
        return buildResponse(
                HttpStatus.NOT_FOUND,
                "TAXONOMY_NOT_FOUND",
                exception.getMessage()
        );
    }

    @ExceptionHandler(DuplicateTaxonomyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(
            DuplicateTaxonomyException exception
    ) {
        return buildResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_TAXONOMY",
                exception.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(
            MethodArgumentNotValidException exception
    ) {
        List<ApiError.FieldError> fieldErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError.FieldError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                "VALIDATION_ERROR",
                "Request validation failed.",
                fieldErrors
        );

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Validation error", apiError));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(
            ConstraintViolationException exception
    ) {
        List<ApiError.FieldError> fieldErrors = exception
                .getConstraintViolations()
                .stream()
                .map(violation -> new ApiError.FieldError(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()
                ))
                .toList();

        ApiError apiError = new ApiError(
                "VALIDATION_ERROR",
                "Request validation failed.",
                fieldErrors
        );

        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure("Validation error", apiError));
    }

    private ResponseEntity<ApiResponse<Void>> buildResponse(
            HttpStatus status,
            String code,
            String details
    ) {
        ApiError error = new ApiError(code, details, List.of());
        ApiResponse<Void> response = ApiResponse.failure("Taxonomy error", error);
        return ResponseEntity.status(status).body(response);
    }
}

