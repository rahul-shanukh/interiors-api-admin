package com.polymorphic.media.internal.presentation.rest.taxonomy.dto.response;
//My recommendation: Use ApiResponse<List<TaxonomySchemaResponse>>.
// Avoid creating an unnecessary wrapper DTO just to hold a list.

import java.util.List;

public record TaxonomySchemaListResponse(
        List<TaxonomySchemaResponse> schemas,
        int totalCount
) {}