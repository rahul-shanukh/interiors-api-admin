package com.polymorphic.media.internal.domain.catalog.model;

import com.polymorphic.media.internal.domain.catalog.enums.FieldInputType;

public record FieldDefinition_D(
        String fieldKey,           // "material"  -> the key stored on the asset
        String taxonomyType,       // "MATERIAL"  -> which shelf feeds this dropdown
        FieldInputType inputType,
        boolean required,
        int order,                // position on the form
        int unit,
        int group,
        boolean active
) {}