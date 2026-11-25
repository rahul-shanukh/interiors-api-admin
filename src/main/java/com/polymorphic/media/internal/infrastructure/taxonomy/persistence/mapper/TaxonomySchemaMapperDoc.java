package com.polymorphic.media.internal.infrastructure.taxonomy.persistence.mapper;

import com.polymorphic.media.internal.domain.taxonomy.types.TaxonomySchema;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyType;
import com.polymorphic.media.internal.domain.taxonomy.valueobjects.TaxonomyValue;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomySchemaEntity;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomySchemaValueEntity;
import com.polymorphic.media.internal.infrastructure.taxonomy.persistence.entity.TaxonomyValueStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaxonomySchemaMapperDoc {

    public TaxonomySchema toDomain(TaxonomySchemaEntity entity) {
        if (entity == null) {
            return null;
        }
        Set<TaxonomyValue> valueSet = entity.getValues() == null
                ? Set.of()
                : entity.getValues().stream()
                .map(value -> new TaxonomyValue(value.getValue()))
                .collect(Collectors.toSet());
        return new TaxonomySchema(
                new TaxonomyType(entity.getType()),
                valueSet,
                entity.getDescription()
        );
    }

    public TaxonomySchemaEntity toEntity(TaxonomySchema domain) {
        if (domain == null) {
            return null;
        }

        TaxonomySchemaEntity entity = TaxonomySchemaEntity.builder()
                .type(domain.type().value())
                .description(domain.description())
                .build();

        List<TaxonomySchemaValueEntity> values = domain.values() == null
                ? new ArrayList<>()
                : domain.values().stream()
                .map(value -> {
                    TaxonomySchemaValueEntity valueEntity =
                            new TaxonomySchemaValueEntity();

                    valueEntity.setValue(value.value());
                    valueEntity.setStatus(TaxonomyValueStatus.ACTIVE);
                    valueEntity.setSchema(entity);

                    return valueEntity;
                })
                .toList();

        entity.setValues(values);

        return entity;
    }
}