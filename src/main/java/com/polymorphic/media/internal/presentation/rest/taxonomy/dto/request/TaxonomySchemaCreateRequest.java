package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record TaxonomySchemaCreateRequest(


        @NotBlank(message = "Taxonomy type is required")
        @Size(max = 100, message = "Taxonomy type cannot exceed 100 characters")
        String type,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description,

        Set<
                        @NotBlank(message = "Taxonomy value cannot be blank")
                        @Size(max = 128, message = "Taxonomy value cannot exceed 128 characters")
                                String
                        > values
) {
}
