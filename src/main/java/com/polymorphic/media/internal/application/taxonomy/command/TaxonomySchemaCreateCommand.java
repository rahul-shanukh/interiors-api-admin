
package com.polymorphic.media.internal.application.taxonomy.command;

import java.util.Set;

public record TaxonomySchemaCreateCommand(
        String type,
        String description,
        Set<String> values
) {}
