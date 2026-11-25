package com.polymorphic.media.internal.application.taxonomy.service;

import com.polymorphic.media.internal.application.taxonomy.command.TaxonomySchemaCreateCommand;
import com.polymorphic.media.internal.application.taxonomy.contract.TaxonomySchemaService;
import com.polymorphic.media.internal.application.taxonomy.exception.DuplicateTaxonomyException;
import com.polymorphic.media.internal.application.taxonomy.factory.TaxonomySchemaFactory;
import com.polymorphic.media.internal.application.taxonomy.result.TaxonomySchemaResult;
import com.polymorphic.media.internal.domain.taxonomy.repo.TaxonomySchemaRepository;
import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomyValueStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class TaxonomySchemaServiceImpl implements TaxonomySchemaService {

    private final TaxonomySchemaFactory taxonomySchemaFactory;
    private final TaxonomySchemaRepository taxonomySchemaRepository;

    public TaxonomySchemaServiceImpl(
            TaxonomySchemaFactory taxonomySchemaFactory,
            TaxonomySchemaRepository taxonomySchemaRepository
    ) {
        this.taxonomySchemaFactory = taxonomySchemaFactory;
        this.taxonomySchemaRepository = taxonomySchemaRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TaxonomySchemaResult createSchema(TaxonomySchemaCreateCommand command) {
        TaxonomySchema schema = taxonomySchemaFactory.create(command);

        String normalizedType = schema.type().value();
        if (taxonomySchemaRepository.existsByType(normalizedType)) {
            throw new DuplicateTaxonomyException(normalizedType);
        }

        TaxonomySchema saved = taxonomySchemaRepository.save(schema);

        Set<String> values = saved.values().stream()
                .map(TaxonomyValue::value)
                .collect(java.util.stream.Collectors.toSet());

        return new TaxonomySchemaResult(
                saved.type().value(),
                saved.description(),
                values
        );
    }

    @Override
    @Transactional(readOnly = true , rollbackFor = Exception.class)
    public TaxonomySchema getSchema(String type) {
        return null;
    }

    @Override
    public List<TaxonomySchema> getAllSchemas() {
        return List.of();
    }

    @Override
    public TaxonomySchema updateDescription(String type, String description) {
        return null;
    }

    @Override
    public TaxonomySchema addValues(String type, Set<String> values, String reason) {
        return null;
    }

    @Override
    public List<TaxonomyValue> getValues(String type) {
        return List.of();
    }

    @Override
    public TaxonomyValue updateValueStatus(String type, String value, TaxonomyValueStatus status, String reason) {
        return null;
    }
}
