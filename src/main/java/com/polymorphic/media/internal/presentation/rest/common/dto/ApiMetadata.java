//Don't let controllers generate request IDs or manually populate metadata.
// Use a servlet filter to establish request correlation IDs
// and a centralized response factory or helper to
// populate metadata consistently.

package com.polymorphic.media.internal.presentation.rest.common.dto;

public record ApiMetadata(
        String requestId,
        String path,
        String version,
        PageMetadata pagination
) {}
