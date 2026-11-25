package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaxonomySchemaDescriptionUpdateRequest(

        @NotBlank(message = "Description is required")
        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description

) {}
//The cleanest approach is to pass the schema type as a URL path variable,
// not inside the request body.