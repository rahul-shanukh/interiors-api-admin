package com.polymorphic.media.internal.application.taxonomy.contract;

import com.polymorphic.media.internal.application.taxonomy.command.TaxonomySchemaCreateCommand;
import com.polymorphic.media.internal.application.taxonomy.result.TaxonomySchemaResult;
import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomyValueStatus;

import java.util.List;
import java.util.Set;

public interface TaxonomySchemaService {

    TaxonomySchemaResult createSchema(TaxonomySchemaCreateCommand command);

    TaxonomySchema getSchema(String type);

    List<TaxonomySchema> getAllSchemas();

    TaxonomySchema updateDescription(
            String type,
            String description
    );

    TaxonomySchema addValues(
            String type,
            Set<String> values,
            String reason
    );

    List<TaxonomyValue> getValues(String type);

    TaxonomyValue updateValueStatus(
            String type,
            String value,
            TaxonomyValueStatus status,
            String reason
    );
}
