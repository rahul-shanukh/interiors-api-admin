package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record TaxonomySchemaValuesUpdateRequest(

        @NotEmpty(message = "At least one taxonomy value is required")
        Set<
                        @NotBlank(message = "Taxonomy value cannot be blank")
                        @Size(max = 128, message = "Taxonomy value cannot exceed 128 characters")
                                String
                        > values,

        @NotBlank(message = "Reason is required")
        @Size(max = 500, message = "Reason cannot exceed 500 characters")
        String reason
) {}
