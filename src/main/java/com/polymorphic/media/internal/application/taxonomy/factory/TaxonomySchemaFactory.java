package com.polymorphic.media.internal.application.taxonomy.factory;

import com.polymorphic.media.internal.application.taxonomy.command.TaxonomySchemaCreateCommand;
import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyType;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaxonomySchemaFactory {

    public TaxonomySchema create(TaxonomySchemaCreateCommand command) {
        Objects.requireNonNull(command, "Taxonomy schema create command cannot be null");

        TaxonomyType type = new TaxonomyType(command.type());

        if (command.description() == null || command.description().isBlank()) {
            throw new IllegalArgumentException("Taxonomy description cannot be blank");
        }

        Objects.requireNonNull(command.values(), "Taxonomy values cannot be null");

        if (command.values().isEmpty()) {
            throw new IllegalArgumentException("Taxonomy values cannot be empty");
        }

        Set<TaxonomyValue> values = command.values().stream()
                .map(TaxonomyValue::new)
                .collect(Collectors.toSet());

        if (values.size() != command.values().size()) {
            throw new IllegalArgumentException("Taxonomy values must be unique (size of taxonomy values)");
        }

        return new TaxonomySchema(type, values, command.description().trim());
    }
}

