package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.request;

import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomyValueStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//PATCH /api/v1/taxonomies/{type}/values/{value}/status

public record TaxonomyValueStatusRequest(

        @NotNull(message = "Status is required")
        TaxonomyValueStatus status,

        @NotBlank(message = "Reason is required")
        @Size(max = 500, message = "Reason cannot exceed 500 characters")
        String reason
) {}